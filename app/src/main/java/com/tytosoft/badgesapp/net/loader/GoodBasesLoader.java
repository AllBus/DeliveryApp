package com.tytosoft.badgesapp.net.loader;

import android.os.AsyncTask;

import com.kos.fastuimodule.common.net.IJSONConstructor;
import com.kos.fastuimodule.good.common.core.IContexter;
import com.kos.fastuimodule.good.common.system.GoodPostArguments;
import com.tytosoft.delivery.net.loader.GoodGetLoader;


/**
 * Схема загрузки
 *
 * pre {startLoad} -> background {newElement} ->
 * 		switch {
 * 			onlybase -> post(false) {push, endLoad} !!
 *			needUpdate -> asynh ->
 *							switch{
 *								true -> post(true) {push} ~> zaprosBackground {newElement} -> zaprosPost {push , endLoad} !!
 *								false -> post(false) {push, endLoad} !!
 *							}
 *			_	-> 	post(false) {push, endLoad} !!
 * 		}
 * Created by Kos on 25.09.2015.
 */

public class GoodBasesLoader extends AsyncTask<String, Void, Boolean> {
	private final boolean defearDownload;
	private final IContexter info;
	private final String url;
	private final IJSONConstructor constructor;
	private final GoodPostArguments arguments;
	private final boolean onlyBase;
	private final boolean onlyServer;


	public GoodBasesLoader(IContexter info, String url, IJSONConstructor constructor, GoodPostArguments arguments,
						   boolean onlyServer,
						   boolean defearDownload,
						   boolean onlyBase) {

		this.info = info;
		this.url = url;
		this.constructor = constructor;
		this.arguments=arguments;
		this.defearDownload=defearDownload;
		this.onlyBase=onlyBase;
		this.onlyServer=onlyServer;
	}

	protected void onPreExecute() {
		arguments.startLoad();
		super.onPreExecute();
	}

	protected Boolean doInBackground(String... urls) {
		return GoodGetLoader.background(info, url, constructor, arguments,onlyServer,defearDownload,onlyBase);
	}

	/**
	 *
	 * @param result если результат false значит загрузка окончена и мы должны их извлечь из загрузки
	 */
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);

		if (this.info != null) {
			this.info.push();
		}
		if (!result) {
			arguments.endLoad();
		}
	}
}
