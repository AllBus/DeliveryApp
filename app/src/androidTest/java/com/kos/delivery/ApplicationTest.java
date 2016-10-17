package com.kos.delivery;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.kos.delivery.net.DataStore;


/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
	public ApplicationTest() {
		super(Application.class);


		DataStore.info().setContext(this.getContext());

	}
}