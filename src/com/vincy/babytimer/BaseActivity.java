package com.vincy.babytimer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

public class BaseActivity extends Activity {

	public static List<Activity> activitys;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (activitys == null) {
			activitys = new ArrayList<Activity>();
		}
		activitys.add(this);
	}

	protected void exitApp(Context context) {
		Iterator<Activity> iterator = activitys.iterator();
		while (iterator.hasNext()) {
			Activity activity = (Activity) iterator.next();
			if (activity != null) {
				activity.finish();
			}
		}
	}
}
