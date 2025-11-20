package com.orangeHRM.test;

import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;

public class DummyClass2 extends  BaseClass{
	@Test
	public void dummyTest2() {
		String title = getDriver().getTitle();
		assert title.equals("OrangeHRM"): "Title does not match!";
		System.out.println("Dummy test passed. Title: " + title);
	}

}
