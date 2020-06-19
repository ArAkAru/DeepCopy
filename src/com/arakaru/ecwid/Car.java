package com.arakaru.ecwid;

public class Car {
	private String name;
	private int doorNum;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getDoorNum() {
		return doorNum;
	}
	public void setDoorNum(int doorNum) {
		this.doorNum = doorNum;
	}
	@Override
	public String toString() {
		return "Car [name=" + name + ", doorNum=" + doorNum + "]";
	}
	
}
