package com.example.demo.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;

public final class TestUtils {

	private TestUtils() {}
	
	
	public static <T> void printProperty(Class<T> dataClass, T data) {
		
		List<Method> methods = new ArrayList<>();
		CollectionUtils.addAll(methods, dataClass.getMethods());
		Predicate<Method> predicate = m -> m.getName().startsWith("get");
		List<Method> getterMethods = methods.stream().filter(predicate.or(m -> m.getName().startsWith("is")))
				.collect(Collectors.toList());
		
		getterMethods.forEach(m -> {

			try {
				System.err.println(m.invoke(data));
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				
				e.printStackTrace();
			}
		});
		System.err.println("-----------------------神秘分割线-----------------------");
	}
	
	public static <T> void printProperty(Class<T> dataClass, List<T> data) {
		
		List<Method> methods = new ArrayList<>();
		CollectionUtils.addAll(methods, dataClass.getMethods());
		Predicate<Method> predicate = m -> m.getName().startsWith("get");
		List<Method> getterMethods = methods.stream().filter(predicate.or(m -> m.getName().startsWith("is")))
				.collect(Collectors.toList());
		data.forEach(d -> {

			getterMethods.forEach(m -> {

				try {
					System.err.println(m.invoke(d));
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					
					e.printStackTrace();
				}
			});
			System.err.println("-----------------------神秘分割线-----------------------");
		});
	}
}
