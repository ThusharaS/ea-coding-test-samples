package tests;

import static io.restassured.RestAssured.given;

import java.util.List;

import org.openqa.selenium.By;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import junit.framework.Assert;
import setup.TestSetup;
import utils.Config;

public class WebpageTest extends TestSetup {

	Response response = null;

	//First test is to get the api response
	@Test(priority = 0)
	public void getRequest() {
		response = given()
				.contentType(ContentType.JSON)
				.when()
				.get(Config.getProperty("apipath"))
				.then()
				.extract().response();

		if(response.statusCode()!=200) {
			response = given()
					.contentType(ContentType.JSON)
					.when()
					.get(Config.getProperty("apipath"))
					.then()
					.extract().response();
		}

		Assert.assertEquals(200, response.statusCode());
	}

	//Second test is to validate the api response in the UI
	@Test(priority=1)
	public void webpageTest() throws InterruptedException {

		SoftAssert sassert = new SoftAssert();
		String url = Config.getProperty("weburl")+Config.getProperty("webpath");
		System.out.println(url);

		getDriver().get(url);
		Thread.sleep(3000);

		JsonPath jsonPathEvaluator = response.jsonPath();

		//		System.out.println(jsonPathEvaluator.get("name[0]").toString());

		List<String> jsonResponse = response.jsonPath().getList("$");
		System.out.println(jsonResponse.size());
		
		List<String> jsonResponse_name = response.jsonPath().getList("name");
		System.out.println(jsonResponse_name);
		
		List<String> jsonResponse_band = response.jsonPath().getList("bands");
		System.out.println(jsonResponse_band);
		
//		List<Map<String, String>> band = response.jsonPath().getList("bands");
//		System.out.println(band.get(0).get("name"));
		
		sassert.assertTrue(!jsonResponse_name.contains(null),"jsonResponse_name contains null!");
		sassert.assertTrue(!jsonResponse_band.contains(null),"jsonResponse_band contains null!");
		sassert.assertEquals(jsonResponse_name.size(), jsonResponse_band.size());
		
		jsonResponse_name.remove(null);
		
		for(String name : jsonResponse_name) {
			
				sassert.assertTrue(getDriver().findElements(By.xpath("//*[contains(text(),'"+name+"')]")).size()>0, name + " field not present in the website!");
		}
		
		
		sassert.assertAll();


	}



}
