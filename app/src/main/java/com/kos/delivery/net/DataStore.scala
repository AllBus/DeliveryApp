package com.kos.delivery.net

import com.kos.fastuimodule.good.common._
import com.kos.fastuimodule.good.common.bus.BusProvider
import com.kos.fastuimodule.good.common.core.IContexter
import com.kos.fastuimodule.good.common.model.IEntity
import com.kos.fastuimodule.good.common.system.collection.ListLoadingData
import com.tytosoft.badgesapp.model.{SystemModel, OrderModel, ProductModel}
import com.tytosoft.badgesapp.model.data.UserProfile
import com.tytosoft.badgesapp.net.{BusConstants, Program}
import com.tytosoft.delivery.data.AppPrefererences
import com.tytosoft.delivery.model.{CodeData, AdsModel, ListModel, SectionModel}
import com.tytosoft.delivery.model.data.Korzina
import com.tytosoft.delivery.net.{GoodApi, TAllInfo}
import org.json.JSONObject


/**
  * Created by Kos on 06.07.2016.
  */
object DataStore {

	var info: IContexter = new TAllInfo()
	val lastProfile:UserProfile=new UserProfile()
	var lastKorzina= new Korzina()

	lazy val preferences=new AppPrefererences(this.info.getContext(),lastProfile,lastKorzina)


	/** вызовите этот метод если нужно сохранить данные в  базу
	  *
	  * @param data данные которые сохраняем
	  * @return успешно ли были сохранены данные
	  */
	def saveData(data:Any):Boolean={
		//todo: надо сделать сохранение для lastProfile, lastKorzina
		data match{
			case profile:UserProfile ⇒
				preferences.saveProfile(profile)
			//	profile.saveData()
			case korzina:Korzina ⇒
				preferences.saveKorzina(korzina)
			case _ ⇒

		}

		return true
	}

	val products= standardList[ProductModel](
		Program.API_PRODUCT,
		ProductModel.NULL,
		new ProductModel(_),
		BusConstants.product
	)

	val sections= standardList[SectionModel](
		Program.API_SECTION,
		SectionModel.NULL,
		new SectionModel(_),
		BusConstants.section
	)

	val lists= standardList[ListModel](
		Program.API_LIST,
		ListModel.NULL,
		new ListModel(_),
		BusConstants.list
	)

	val ads= standardList[AdsModel](
		Program.API_ADS,
		AdsModel.NULL,
		new AdsModel(_),
		BusConstants.ads
	)

	val orders= standardListOffline[OrderModel](
		Program.API_ORDER,
		OrderModel.NULL,
		new OrderModel(_),
		BusConstants.order
	)

	@inline private[this] def standardList[T<:IEntity](api:String,
													   loaderItem: ⇒ T,
													   constructor: JSONObject ⇒ T,
													   busConstant:Seq[ID] ⇒ Object) = {
		new ListLoadingData[T](
			loaderItem,
			constructor,
			GoodApi.load(api, Program._ACTION_EMPTY),
			busConstant,
			BusConstants.startLoad(api),
			BusConstants.endLoad(api)
		)
	}

	@inline private[this] def standardListOffline[T<:IEntity](api:String,
											   loaderItem: ⇒ T,
											   constructor: JSONObject ⇒ T,
											   busConstant:Seq[ID] ⇒ Object) = {
		new ListLoadingData[T](
			loaderItem,
			constructor,
			GoodApi.loadBase(api, Program._ACTION_EMPTY),
			busConstant,
			BusConstants.startLoad(api),
			BusConstants.endLoad(api)
		)
	}


	def updatePreferences(model: SystemModel): Unit ={

		preferences.systemModel=model
		preferences.saveModel()

		BusProvider.post(BusConstants.updateSystem)
	}

	def getListFor(element:SectionModel): ID ={
		lists.allItems().find(x ⇒ lists.get(x).parentId==element.getId).getOrElse(ListModel.NONE_ID)
	}

	def getListTop:ID= {
		val typ=preferences.systemModel.mainPageKind

		lists.allItems().find{x ⇒
			val ll=lists.get(x)
			(ll.parentId==ListModel.NULL_ID) && (ll.getKind==typ)}.getOrElse(ListModel.NONE_ID)
	}

	def getListAds:ID={
		val typ=Program.LIST_ADV

		lists.allItems().find{x ⇒
			val ll=lists.get(x)
			(ll.parentId==ListModel.NULL_ID) && (ll.getKind==typ)}.getOrElse(ListModel.NONE_ID)
	}

	def loadFirstItems(listId:ID):Unit={
		val list=lists.get(listId)
		if (!list.isNull) {
			val ids = list.getList.take(50).toSet //Взять первые 50 элементов
			if (ids.nonEmpty) {
				list.getKind match {
					case Program.LIST_PRODUCT ⇒
						if (products.get(ids.head).isNull)
							products.loadItems(ids)
					case Program.LIST_SECTION ⇒
						if (sections.get(ids.head).isNull)
							sections.loadItems(ids)
					case Program.LIST_ADV ⇒
						if (ads.get(ids.head).isNull)
							ads.loadItems(ids)
					case _ ⇒
				}
			}
		}
	}

	def updateCode(data: CodeData): Unit = {
		data.action match{
			case Program.API_ORDER_ADD ⇒

				lastProfile.progressState=data.code
				if (Program.isZakazComplete(data.code)){
					addZakaz(lastKorzina)
					lastKorzina=new Korzina()
					BusProvider.post(BusConstants.clearKorzina)
				}
				BusProvider.post(BusConstants.updateZakaz)

			case _ ⇒
		}
	}

	def addZakaz(korzina: Korzina): Unit ={
		val order=new OrderModel(id=System.currentTimeMillis() ,korzina,preferences)
		CommonEventSaver.saveSon(info,info.tableName(Program.API_ORDER),Seq(order.getId → order.save()))
		orders.put(Seq(order), postUpdate = true)
	}

	def loadZakazHistory(): Unit ={
		if (orders.isEmpty){
			orders.loadItems(GoodApi.EMPTY_IDS)
		}
	}
}
