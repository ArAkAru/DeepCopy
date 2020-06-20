package com.arakaru.ecwid;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;

public class CopyUtils {
	public static <V, K, T> T deepCopy(T originalObj)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {
		Constructor[] con = originalObj.getClass().getDeclaredConstructors();
		Constructor<T> declaredConstructor = null;

		T clone = null;
		if (con.length == 0) {
			declaredConstructor = (Constructor<T>) originalObj.getClass().getConstructor();
			clone = declaredConstructor.newInstance();

		} else {
			for (Constructor<?> a : con) {
				Object args[] = new Object[a.getParameters().length];
				for (int i = 0; i < args.length; i++) {
					if (a.getParameters()[i].getType().isPrimitive()) {
						args[i] = 0;
					} else {
						args[i] = null;
					}
				}
				declaredConstructor = (Constructor<T>) originalObj.getClass()
						.getDeclaredConstructor((a.getParameterTypes()));
				clone = declaredConstructor.newInstance(args);
				break;
			}

		}
		Field[] fields = clone.getClass().getDeclaredFields();

		for (Field field : fields) {
			field.setAccessible(true);
			if (field.getType().isArray()) {

				Object array = Array.newInstance(field.getType().getComponentType(),
						Array.getLength(field.get(originalObj)));
				// ЕСЛИ ЭТО МАССИВ ОБЪЕКТОВ КРОМЕ STRING
				if (field.getType().getName().startsWith("[L") && !field.getType().getName().contains("String")) {
					for (int i = 0; i < Array.getLength(field.get(originalObj)); i++) {
						Array.set(array, i, deepCopy(Array.get(field.get(originalObj), i)));
					}
				} else {
					System.arraycopy(field.get(originalObj), 0, array, 0, Array.getLength(field.get(originalObj)));
				}
				field.set(clone, array);
				continue;
			}

			if (field.getType().isPrimitive() || isPrim(field.getType().getName())) {
				field.set(clone, field.get(originalObj));
				continue;
			}

			if (field.getType().getSimpleName().contains("List") || field.getType().getSimpleName().contains("Set")) {

				Class<?> clazz = field.get(originalObj).getClass();
				Constructor<?> constructor = (Constructor<?>) clazz.getConstructor();
				Collection<Object> copylist = (Collection<Object>) constructor.newInstance();
				Collection<?> originalList = (Collection<?>) field.get(originalObj);
				originalList.stream().forEach(value -> {
					copylist.add(getValue(value));
				});
				field.set(clone, copylist);
				continue;
//			
			}

			if (field.getType().getSimpleName().contains("Map")) {

				Map<K, V> origmap = (Map<K, V>) field.get(originalObj);
				Class<?> clazz = field.get(originalObj).getClass();
				Constructor<?> constructor = (Constructor<?>) clazz.getConstructor();
				Map<K, V> map = (Map<K, V>) constructor.newInstance();
				origmap.values().forEach(mapValue -> {
					map.put(getValue(getKey(origmap, mapValue)), getValue(mapValue));
				});
				field.set(clone, map);
				continue;

			}
			//если поле это собственный класс
			else {
				field.set(clone, deepCopy(field.get(originalObj)));
				continue;
			}

		}
		return clone;
	}

	private static <K, V> K getKey(Map<K, V> Map, V value) {
		for (K Key : Map.keySet()) {
			if (Map.get(Key).equals(value)) {
				return Key;
			}
		}
		return null;

	}

	private static <V> V getValue(V mapValue) {
		V copyMapValue = null;
		if (isPrim(mapValue.getClass().getName())) {
			copyMapValue = mapValue;
		} else {
			try {
				copyMapValue = deepCopy(mapValue);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException
					| ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return copyMapValue;
	}
	//проверяем класс оболочка ли поле
	private static boolean isPrim(String name) {
		if (name.contains("String") || name.contains("Integer") || name.contains("Double") || name.contains("Byte")
				|| name.contains("Boolean") || name.contains("Long") || name.contains("Short")
				|| name.contains("Character")) {
			return true;

		}
		return false;

	}

}
