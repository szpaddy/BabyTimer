package com.vincy.babytimer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.vincy.babytimer.utils.ScreenUtil;
import com.vincy.babytimer.utils.ScreenUtil.Screen;

public class BaseActivity extends Activity {
	private static final String TAG = "BaseActivity";

	public static List<Activity> activitys;

	private GestureDetector mGestureDetector;
	protected Screen screen;
	private GestureListener mGestureListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		screen = ScreenUtil.getScreenPix(this);
		mGestureDetector = new GestureDetector(new MySimpleOnGestureListener());
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

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		if (mGestureDetector.onTouchEvent(event)) {
			event.setAction(MotionEvent.ACTION_CANCEL);
		}
		return super.dispatchTouchEvent(event);
	}

	public void setGestureListener(GestureListener mGestureListener) {
		this.mGestureListener = mGestureListener;
	}

	class MySimpleOnGestureListener extends SimpleOnGestureListener {

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			Log.d(TAG, "onFling...");
			// 限制必须得划过屏幕的1/4才能算划过
			float x_limit = screen.widthPixels / 4;
			if (e1.getX() - e2.getX() > x_limit) {
				// Fling left
				if (mGestureListener != null) {
					Log.d(TAG, "onFlingLeft...");
					mGestureListener.onFlingLeft();
				}
			} else if (e2.getX() - e1.getX() > x_limit) {
				// Fling right
				if (mGestureListener != null) {
					Log.d(TAG, "onFlingRight...");
					mGestureListener.onFlingRight();
				}
			}
			return false;
		}
	}

}