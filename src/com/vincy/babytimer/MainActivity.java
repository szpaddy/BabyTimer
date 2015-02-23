package com.vincy.babytimer;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnItemLongClick;
import com.vincy.babytimer.adapter.BabyActionAdapter;
import com.vincy.babytimer.utils.DateUtil;
import com.vincy.babytimer.utils.MainToast;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

	@ViewInject(R.id.lv_babyaction)
	private ListView lv_babyaction;

	@ViewInject(R.id.tv_date)
	private TextView tv_date;

	@ViewInject(R.id.layout_actionbtns)
	private LinearLayout layout_actionbtns;

	private BabyActionAdapter babyActionAdapter;

	private long lastBackClickTime;

	private DbUtils db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ViewUtils.inject(this);
		db = DbUtils.create(this);

		babyActionAdapter = new BabyActionAdapter(this);
		lv_babyaction.setAdapter(babyActionAdapter);

		updateActionDate(DateUtil.getCurrentDate());
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		db.close();
	}

	@OnClick(value = { R.id.btn_breast_begin, R.id.btn_breast_end,
			R.id.btn_pee, R.id.btn_poop, R.id.btn_vomit, R.id.btn_water })
	private void onNormalActionButtonClick(View view) {
		String action = (String) ((Button) view).getText();
		insertBabyAction(action);
	}

	@OnClick(R.id.btn_remark)
	private void onRemarkActionButtonClick(View view) {
		final EditText et = new EditText(this);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("备注");
		builder.setView(et);
		builder.setPositiveButton("保存",
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						insertBabyAction(et.getText().toString());
					}
				});
		builder.setNegativeButton("取消", null);
		builder.show();

	}

	@OnClick(R.id.btn_date)
	private void onDateButtonClick(View view) {
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		int year = calendar.get(Calendar.YEAR);
		int monthOfYear = calendar.get(Calendar.MONTH);
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		DatePickerDialog dpd = new DatePickerDialog(this,
				new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						updateActionDate(String.format("%d-%02d-%02d", year,
								monthOfYear + 1, dayOfMonth));
					}
				}, year, monthOfYear, dayOfMonth);
		dpd.show();
	}

	@OnItemLongClick(R.id.lv_babyaction)
	private boolean onBabyActionItemLongClick(AdapterView parent, View view,
			int position, long id) {
		if (isActionForToday()) {
			final BabyAction item = (BabyAction) babyActionAdapter
					.getItem(position);
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("删除确认");
			builder.setMessage(String.format("%s %s", item.getDisplayTime(),
					item.getAction()));
			builder.setPositiveButton("确认",
					new android.content.DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							try {
								db.deleteById(BabyAction.class, item.getId());
								updateBabyActionList();
							} catch (DbException e) {
								MainToast.show(
										MainActivity.this,
										String.format("保存%s %s失败",
												item.getDisplayTime(),
												item.getAction()),
										Toast.LENGTH_SHORT);
							}
						}
					});
			builder.setNegativeButton("取消", null);
			builder.show();
		}
		return true;
	}

	private boolean isActionForToday() {
		return DateUtil.getCurrentDate().equals(tv_date.getText());
	}

	private void updateActionDate(String date) {
		tv_date.setText(date);
		if (isActionForToday()) {
			layout_actionbtns.setVisibility(View.VISIBLE);
		} else {
			layout_actionbtns.setVisibility(View.GONE);
		}
		updateBabyActionList();
	}

	private void insertBabyAction(String action) {
		BabyAction babyAction = new BabyAction(action,
				DateUtil.getCurrentDateTime());
		try {
			db.save(babyAction);
			updateBabyActionList();
		} catch (DbException e) {
			MainToast.show(this, String.format("保存%s失败", action),
					Toast.LENGTH_SHORT);
		}
	}

	private void updateBabyActionList() {
		try {
			Selector selector = Selector.from(BabyAction.class)
					.where("time", "like", tv_date.getText() + "%")
					.orderBy("time", true);
			LogUtils.d(selector.toString());
			List<BabyAction> list = db.findAll(selector);
			babyActionAdapter.setList(list);
			lv_babyaction.smoothScrollToPosition(0);
		} catch (DbException e) {
			MainToast.show(this, "显示列表信息失败", Toast.LENGTH_SHORT);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (System.currentTimeMillis() - this.lastBackClickTime > 2000L) {
				MainToast.show(this, "再按一次退出程序", Toast.LENGTH_SHORT);
				this.lastBackClickTime = System.currentTimeMillis();
			} else {
				exitApp(this);
				System.exit(0);
			}
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

}
