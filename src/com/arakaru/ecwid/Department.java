package com.arakaru.ecwid;

import java.util.Arrays;
import java.util.List;

public class Department {
	String name;
	int place[];
	@Override
	public String toString() {
		return "Department [name=" + name + ", place=" + Arrays.toString(place) + ", adress=" + adress + "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int[] getPlace() {
		return place;
	}
	public void setPlace(int[] place) {
		this.place = place;
	}
	public List<Adress> getAdress() {
		return adress;
	}
	public void setAdress(List<Adress> adress) {
		this.adress = adress;
	}
	List<Adress>adress;
	
}
