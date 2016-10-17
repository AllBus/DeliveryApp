package com.tytosoft.delivery.net;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.kos.fastuimodule.good.common.DataColumns.*;


/**
 * Created by Kos on 10.03.2016.
 */
public class DataBase extends SQLiteOpenHelper {

	public static final int DATABASE_VERSION = 2;
	public static final String DATABASE_NAME = "list3.db";

	public static final String TABLE_SECTION = "sections_table";
	public static final String TABLE_PRODUCT = "products_table";
	public static final String TABLE_ADS = "ads_table";
	public static final String TABLE_ORDER = "order_table";
	public static final String TABLE_LIST_NAME = "lists_table";
	public static final String TABLE_OTHER_NAME = "others_table";

	/**
	 * Список таблиц имеющих одинаковый набор столбцов projection
	 */
	public static final String[] defTables = {
			TABLE_SECTION,
			TABLE_PRODUCT,
			TABLE_LIST_NAME,
			TABLE_OTHER_NAME,
			TABLE_ORDER,
			TABLE_ADS
	};


	public DataBase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		for (String table : defTables) {
			try {
				db.execSQL(CREATE + table + " (" + DEFAULT_TABLE + " )");
			} catch (Exception ignored) {
				//ignored.printStackTrace();
			}
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		for (String table : defTables) {
			try {
				db.execSQL(DROP + table);
			} catch (Exception ignored) {
				//ignored.printStackTrace();
			}
		}
		onCreate(db);

	}
}