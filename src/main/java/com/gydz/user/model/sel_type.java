package com.gydz.user.model;

public class sel_type {
	
	private int id;

	private String sel_type;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSel_type() {
		return sel_type;
	}

	public void setSel_type(String sel_type) {
		this.sel_type = sel_type;
	}

	@Override
	public String toString() {
		return "sel_type [id=" + id + ", sel_type=" + sel_type + "]";
	}

	
}
