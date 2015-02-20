package com.vincy.babytimer.utils;

import android.content.Context;
import android.widget.Toast;

public class MainToast {
	private static Toast toast;

	public static void show(Context context, String text, int duration) {
		if (toast == null) {
			toast = Toast.makeText(context, text, duration);
		} else {
			toast.setText(text);
			toast.setDuration(duration);
		}
		toast.show();
	}
}
