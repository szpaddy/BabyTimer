package com.vincy.babytimer.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vincy.babytimer.BabyAction;
import com.vincy.babytimer.R;

public class BabyActionAdapter extends BaseAdapter {

	private Context context;
	private List<BabyAction> list;

	public BabyActionAdapter(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		if (list == null) {
			return 0;
		} else {
			return list.size();
		}
	}

	@Override
	public Object getItem(int position) {
		if (list == null) {
			return null;
		} else {
			return list.get(position);
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LinearLayout.inflate(this.context,
					R.layout.item_babyaction, null);
			viewHolder.tv_time = (TextView) convertView
					.findViewById(R.id.tv_time);
			viewHolder.tv_action = (TextView) convertView
					.findViewById(R.id.tv_action);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		bindView(viewHolder, position);
		return convertView;
	}

	private void bindView(ViewHolder viewHolder, int position) {
		BabyAction item = (BabyAction) this.getItem(position);
		viewHolder.tv_action.setText(item.getAction());
		viewHolder.tv_time.setText(item.getDisplayTime());
	}

	private class ViewHolder {
		TextView tv_time;
		TextView tv_action;
	}

	public void setList(List<BabyAction> list) {
		this.list = list;
		this.notifyDataSetChanged();
	}

}
