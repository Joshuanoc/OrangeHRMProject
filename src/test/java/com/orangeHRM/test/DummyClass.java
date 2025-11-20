package com.orangeHRM.test;

import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;

public class DummyClass extends  BaseClass{
	@Test
	public void dummyTest() {
		String title = getDriver().getTitle();
		assert title.equals("OrangeHRM"): "Title does not match!";
		System.out.println("Dummy test passed. Title: " + title);
	}

}
