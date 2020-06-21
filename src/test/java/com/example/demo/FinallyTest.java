package com.example.demo;

public class FinallyTest {

	public static void main(String[] args) {

		System.err.println(f(100));
		System.err.println(f(1));
	}

	static int f(int input) {

		try {

			throw new NullPointerException();
		} 
		catch (NullPointerException e) {
			
			System.err.println("NullPointerException");
			return input + 2;
		}catch (Exception e) {
			
			System.err.println("Exception");
			return input + 2;
		} finally {

			if (input != 1) {

				return input + input;
			}
		}
	}

}
