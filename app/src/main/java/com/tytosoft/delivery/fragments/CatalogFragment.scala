package com.tytosoft.delivery.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.{LayoutInflater, View, ViewGroup}
import com.kos.fastuimodule.good.common.ID
import com.tytosoft.delivery.model.utils.SONS
import com.tytosoft.delivery.R

/**
  * A simple {@link Fragment} subclass.
  */
object CatalogFragment{

	val ARG_ID="arg_id"

	def apply(id: ID): CatalogFragment ={
		val fragment=new CatalogFragment
		val args = new Bundle()
		SONS.put(args,ARG_ID,id)
		fragment.setArguments(args)
		fragment
	}
}

class CatalogFragment extends Fragment {

	override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle): View = {
		return inflater.inflate(R.layout.fragment_catalog, container, false)
	}
}