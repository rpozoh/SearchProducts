package Home;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.java.After;

import utils.WD;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/Home",
tags = "@SearchProducts",
glue = "Home")
public class HomeRunner {
	
	@After("@End")
	public static void closeFeature() {
		WD.closeDriver();
	}
}