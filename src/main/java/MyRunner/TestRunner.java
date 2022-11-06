package MyRunner;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;


	@RunWith(Cucumber.class)
	@CucumberOptions(
			features = "src/main1/java/Features/postcall.feature",
			glue={"stepDefinitions"},
			plugin= {"pretty","html:test-outout", "json:json_output/cucumber.json", "junit:junit_xml/cucumber.xml"}, //to generate different types of reporting
			monochrome = true,
			strict = true,
			dryRun = false
//			tags= {"@sanity", "@smoke"}

	)
	 
	public class TestRunner {
	 
	}
