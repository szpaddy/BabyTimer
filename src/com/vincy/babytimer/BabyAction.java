package com.vincy.babytimer;

import com.lidroid.xutils.db.annotation.Table;

@Table(name = "BabyAction")
public class BabyAction {

	private int id;

	private String babyId;

	private String action;

	private String time;

	public BabyAction() {
	}

	public BabyAction(String babyId, String action, String time) {
		this.babyId = babyId;
		this.action = action;
		this.time = time;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getTime() {
		return time;
	}

	public String getDisplayTime() {
		return time.substring(10, 16);
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBabyId() {
		return babyId;
	}

	public void setBabyId(String babyId) {
		this.babyId = babyId;
	}

}
