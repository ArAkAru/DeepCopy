package com.arakaru.ecwid;

import java.util.List;
import java.util.Map;

public class Employee {
	 	int age;
	    String name;
	    Department dep;
	   
	    Map <String,Car> cars;

		@Override
		public String toString() {
			return "Employee [age=" + age + ", name=" + name + ", dep=" + dep + ", cars=" + cars + "]";
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Department getDep() {
			return dep;
		}

		public void setDep(Department dep) {
			this.dep = dep;
		}

		public Map<String, Car> getCars() {
			return cars;
		}

		public void setCars(Map<String, Car> cars) {
			this.cars = cars;
		}
	   
	   
	  
}
