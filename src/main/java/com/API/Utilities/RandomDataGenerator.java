package com.API.Utilities;

import java.util.Random;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;

/**
 * @author Shivakumar
 *
 */

public class RandomDataGenerator {

	public static String getFirstName() {
		String generatedString = RandomStringUtils.randomAlphabetic(2).toLowerCase();
		return (new Faker().address().firstName() + generatedString);
	}

	public static String getLastName() {
		String generatedString = RandomStringUtils.randomAlphabetic(2).toLowerCase();
		return (new Faker().address().lastName() + generatedString);
	}

	public static String getUserName() {
		String generatedString = RandomStringUtils.randomAlphabetic(3).toLowerCase();
		return (new Faker().address().firstName() + generatedString);
	}

	public static String getPassword() {
		String generatedString = new Faker().address().firstName()
				+ RandomStringUtils.randomAlphabetic(3).toLowerCase();
		int randomInt = new Random().nextInt(1000);

		return (generatedString + "@" + randomInt);
	}

	public static String getEmail() {
		String generatedString = new Faker().address().firstName() + RandomStringUtils.randomAlphabetic(3);
		int randomInt = new Random().nextInt(1000);
		return (generatedString.toLowerCase() + randomInt + "@gmail.com");
	}

	@Test
	public void Testing() {
		System.out.println(getFirstName());
		System.out.println(getLastName());
		System.out.println(getUserName());
		System.out.println(getPassword());
		System.out.println(getEmail());
//	FakeValuesService faker = new FakeValuesService(
//		      new Locale("en-US"), new RandomService());
//		faker.letterify("12??89"); //will return something like "12hZ89"
//		faker.numerify("ABC##EF"); //will return something like "ABC99EF"
//		faker.bothify("12??##ED"); //will return something like "12iL27ED"
//		faker.regexify("[a-z1-9]{4}"); //will return something like "6bJ1"

	}

}
