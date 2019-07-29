package com.gydz.user.model;

public class sel_name {
	
	private int id;

	private String sel_name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSel_name() {
		return sel_name;
	}

	public void setSel_name(String sel_name) {
		this.sel_name = sel_name;
	}

	@Override
	public String toString() {
		return "sel_name [id=" + id + ", sel_name=" + sel_name + "]";
	}

	
}
