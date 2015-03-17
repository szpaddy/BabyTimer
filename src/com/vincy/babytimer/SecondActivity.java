package com.vincy.babytimer;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnCompoundButtonCheckedChange;

@ContentView(R.layout.activity_second)
public class SecondActivity extends BaseActivity implements GestureListener {
	@ViewInject(R.id.sw_play_heardbeat)
	private Switch sw_play_heardbeat;

	/** SoundPool用于快速播放小而短的声音 常见于游戏 */
	private SoundPool sp;
	private int soundid;
	private boolean isPlaySoundRequired = false;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				sw_play_heardbeat.setChecked(true);
				break;
			}

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setGestureListener(this);

		ViewUtils.inject(this);

		// 初始化需要写在OnCreate中 因为其为异步初始化
		sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		soundid = sp.load(this, R.raw.heartbeat, 1);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@OnCompoundButtonCheckedChange(R.id.sw_play_heardbeat)
	private void onSwitchPlayHeartbeatSoundCheckedChange(
			CompoundButton buttonView, boolean isChecked) {
		isPlaySoundRequired = isChecked;
		if (isChecked) {
			handler.postDelayed(runnable, getdelayMillis());
		}
	}

	private Runnable runnable = new Runnable() {
		public void run() {
			if (isPlaySoundRequired) {
				if (!sw_play_heardbeat.isChecked()) {
					Message message = new Message();
					message.what = 1;
					handler.sendMessage(message);
				}

				sp.play(soundid, 1, 1, 0, 0, 1);
				handler.postDelayed(this, getdelayMillis());
			}
		}
	};

	private int getdelayMillis() {
		return 1000;
	}

	@Override
	public boolean onFlingLeft() {
		return false;
	}

	@Override
	public boolean onFlingRight() {
		showSecondScreen();
		return false;
	}

	private void showSecondScreen() {
		Intent mainIntent = new Intent(this, MainActivity.class);
		this.startActivity(mainIntent);
	}

}
