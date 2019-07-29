package com.gydz.user.model;

public class state {

	private boolean state1;
	private boolean state2;
	public boolean isState1() {
		return state1;
	}
	public void setState1(boolean state1) {
		this.state1 = state1;
	}
	public boolean isState2() {
		return state2;
	}
	public void setState2(boolean state2) {
		this.state2 = state2;
	}
	@Override
	public String toString() {
		return "state [state1=" + state1 + ", state2=" + state2 + "]";
	}
	
}
