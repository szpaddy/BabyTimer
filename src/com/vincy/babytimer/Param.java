package com.vincy.babytimer;

import com.lidroid.xutils.db.annotation.Table;

@Table(name = "Param")
public class Param {

	private int id;

	private String key;

	private String value;

	public Param() {
	}

	public Param(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
