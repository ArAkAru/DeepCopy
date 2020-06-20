package com.arakaru.ecwid;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Main {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		Adress adress1 = new Adress();
		adress1.setName("Pushkin st");
		Adress adress2 = new Adress();
		adress2.setName("Lenin st");
		Adress adress3 = new Adress();
		adress3.setName("Wallker st");
		
		List<Adress> adressList=new LinkedList();
		adressList.add(adress1);
		adressList.add(adress2);
		adressList.add(adress3);
		
		int []place= {1,2,3};
		
		Car bmw = new Car();
		bmw.setName("m5");
		bmw.setDoorNum(4);
		Car audi = new Car();
		audi.setName("TT");
		audi.setDoorNum(2);
		Car fiat = new Car();
		fiat.setName("Linea");
		fiat.setDoorNum(4);
		
		Department department=new Department ();
		department.setName("DEVELOP");
		department.setAdress(adressList);
		department.setPlace(place);
		
		
		Map<String,Car> cars=new HashMap();
		cars.put("1", bmw);
		cars.put("2", audi);
		cars.put("3", fiat);
		
		Employee employee = new Employee();
		employee.setAge(23);
		employee.setName("Arkadiy");
		employee.setCars(cars);
		employee.setDep(department);
		
		
		System.out.println("*********************************************Test #1*******************************************************");
		System.out.println("**Before deepCopy**");
		Employee employeeClone=CopyUtils.deepCopy(employee);
		System.out.println("Original obj: "+employee);
		System.out.println("Clone obj: "+employeeClone);
		
		Car WV = new Car();
		WV.setName("Polo");
		WV.setDoorNum(4);
		Adress adress4 = new Adress();
		adress4.setName("Karavay st");
		adressList.add(adress4);
		
		employee.setAge(33);
		employee.setName("Alex");
		employee.getCars().put("3", WV);
		System.out.println("**After deepCopy**");
		System.out.println("Original obj: "+employee);
		System.out.println("Clone obj: "+employeeClone);
		
		
		/*
		 * Тест второй
		 * 
		 * */
		System.out.println("*********************************************Test #2*******************************************************");
		System.out.println("**Before deepCopy**");
		List<String>books=new LinkedList<String>();
		books.add("Horstmann");
		books.add("Schildt");
		books.add("Eckel");
		
		Man man= new Man("Boris",35,books);
		Man manCopy=CopyUtils.deepCopy(man);
		
		System.out.println("Original obj: "+man);
		System.out.println("Clone obj: "+manCopy);
		
		man.setAge(54);
		man.setName("Ivan");
		List<String>newBooks=new LinkedList<String>();
		newBooks.add("Pushkin");
		newBooks.add("Lafore");
		newBooks.add("Lippman");
		man.setFavoriteBooks(newBooks);
		System.out.println("**After deepCopy**");
		System.out.println("Original obj: "+man);
		System.out.println("Clone obj: "+manCopy);
	}

}
