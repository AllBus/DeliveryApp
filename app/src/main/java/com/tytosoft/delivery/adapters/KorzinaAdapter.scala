package com.tytosoft.delivery.adapters

import android.content.Context
import android.support.annotation.LayoutRes
import android.view.View.OnClickListener
import com.tytosoft.badgesapp.common.adapters.universal.ChangedListAdapter
import com.tytosoft.delivery.adapters.holders.KorzinaProductHolder
import com.tytosoft.delivery.model.data.Korzina

/**
  * Created by Kos on 06.07.2016.
  */
class KorzinaAdapter(
					 context:Context,
					 @LayoutRes layoutRes:Int,
					 korzina:Korzina,
					 itemClick:OnClickListener,
					 plusClick:OnClickListener,
					 minusClick:OnClickListener
					 ) extends ChangedListAdapter(
						context,
						layoutRes,
						korzina,
						new KorzinaProductHolder(_,itemClick,plusClick,minusClick)
){

}
