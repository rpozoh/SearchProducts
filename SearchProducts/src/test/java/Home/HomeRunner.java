package Home;

import org.junit.runner.RunWith;

import io.cucumber.java.After;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import utils.WD;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/Home/Home.feature",
tags = "@SearchProducts",
glue = {""})
public class HomeRunner {
	
	@After("@End")
	public static void closeFeature() {
		WD.closeDriver();
	}
}