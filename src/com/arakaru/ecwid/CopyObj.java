package com.arakaru.ecwid;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;

public class CopyObj {
	public static <V, K, T> T deepCopy(T original)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {
		Constructor[] con = original.getClass().getDeclaredConstructors();
		Constructor<T> declaredConstructor = null;

		T clone = null;
		if (con.length == 0) {
			declaredConstructor = (Constructor<T>) original.getClass().getConstructor();

			clone = declaredConstructor.newInstance();

		} else {
			for (Constructor a : con) {
				
				Object args[] = new Object[a.getParameters().length];
				for (int i = 0; i < args.length; i++) {
					if (a.getParameters()[i].getType().isPrimitive()) {
						args[i] = 0;

					} else {
						args[i] = null;

					}

				}

				declaredConstructor = (Constructor<T>) original.getClass()
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
						Array.getLength(field.get(original)));

				if (field.getType().getName().startsWith("[L") && !field.getType().getName().contains("String")) {

					for (int i = 0; i < Array.getLength(field.get(original)); i++) {

						Array.set(array, i, deepCopy(Array.get(field.get(original), i)));

					}

				} else {
					System.arraycopy(field.get(original), 0, array, 0, Array.getLength(field.get(original)));
				}

				field.set(clone, array);
				continue;
			}

			if (field.getType().isPrimitive() || isPrim(field.getType().getName())) {
				field.set(clone, field.get(original));
				continue;
			}

			if (field.getType().getSimpleName().contains("List") || field.getType().getSimpleName().contains("Set")) {

				Class clazz = field.get(original).getClass();
				Constructor<?> constructor = (Constructor<?>) clazz.getConstructor();
				Collection<Object> list = (Collection<Object>) constructor.newInstance();
				Collection list2 = (Collection) field.get(original);
				list2.stream().forEach(value -> {
					list.add(getValue(value));
				});
				field.set(clone, list);
				continue;
//			
			}

			if (field.getType().getSimpleName().contains("Map")) {

				Map<K, V> origmap = (Map<K, V>) field.get(original);
				Class clazz = field.get(original).getClass();
				Constructor<?> constructor = (Constructor<?>) clazz.getConstructor();
				Map<K, V> map = (Map<K, V>) constructor.newInstance();
				origmap.values().forEach(mapValue -> {
					map.put(getValue(getKey(origmap, mapValue)), getValue(mapValue));
				});
				field.set(clone, map);
				continue;

			}

			else {
				Class clazz = field.getType();
				field.set(clone, deepCopy(field.get(original)));
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

	private static boolean isPrim(String name) {
		if (name.contains("String") || name.contains("Integer") || name.contains("Double") || name.contains("Byte")
				|| name.contains("Boolean") || name.contains("Long") || name.contains("Short")
				|| name.contains("Character")) {
			return true;

		}
		return false;

	}

}
