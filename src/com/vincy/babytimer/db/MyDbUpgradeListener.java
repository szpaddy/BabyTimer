package com.vincy.babytimer.db;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.DbUtils.DbUpgradeListener;
import com.lidroid.xutils.exception.DbException;

public class MyDbUpgradeListener implements DbUpgradeListener {

	@Override
	public void onUpgrade(DbUtils db, int oldVersion, int newVersion) {
		if (oldVersion == 1 && newVersion == 2) {
			try {
				db.execNonQuery("alter table BabyAction add column babyId text default '1';");
			} catch (DbException e) {
				e.printStackTrace();
			}
		}
	}

}
