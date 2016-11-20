package com.tytosoft.delivery;

import android.app.Application;

import com.tytosoft.delivery.net.DataStore;

/**
 * Created by Kos on 05.07.2016.
 */
public class App extends Application {
	public int badgeCount = 1;//todo: example
	public int zakazSize =0;//todo: example

	@Override
	public void onCreate() {
		super.onCreate();
		DataStore.info().setContext(this.getApplicationContext());

//		if (!Glide.isSetup()) {
//			GlideBuilder gb = new GlideBuilder(this);
//			DiskCache dlw = DiskLruCacheWrapper.get(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/myCatch/"), 250 * 1024 * 1024);
//			gb.setDiskCache(dlw);
//			Glide.setup(gb);
//		}

	}
}
