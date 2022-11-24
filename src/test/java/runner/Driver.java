package runner;

import static org.hamcrest.Matchers.equalTo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Map;

import org.hamcrest.Matcher;
import org.json.simple.JSONObject;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.SSLConfig;
import com.jayway.restassured.matcher.ResponseAwareMatcher;
import com.jayway.restassured.response.ResponseBody;
import com.jayway.restassured.response.ValidatableResponse;
import com.jayway.restassured.specification.RequestSpecification;

import utils.HelperUtil;

public class Driver {

	private String uri;
	private ValidatableResponse response;
	private String contentType;
	private String body;
	private static final String GET = "GET";
	private static final String POST = "POST";
	private static final String RANDOM = "random";

	public void sendRequest(String reqestMethod) throws KeyManagementException, UnrecoverableKeyException,
			NoSuchAlgorithmException, KeyStoreException, FileNotFoundException {

		if (GET.equalsIgnoreCase(reqestMethod)) {

			RequestSpecification request = setSSLConfig();
			request.header("x-correlation-id", "1234");
			request.header("x-client-secret", "1234");
			request.header("x-client-id", "1234");
			/*
			 * response = given().header("x-correlation-id",
			 * "abcd123").when().get(uri).then();
			 */
			/* response = given().when().get(uri).then(); */
			response = request.get(uri).then();
		} else if (POST.equalsIgnoreCase(reqestMethod)) {
			RequestSpecification request = RestAssured.given()
					.config(RestAssured.config().sslConfig(new SSLConfig().with().trustStoreType("JKS").and()
							.trustStore(ResourceUtils.getFile("classpath:certs/apix.jks"), "njclabs")));
			request.header("Content-Type", this.contentType);
			request.body(this.body);

			response = request.post(uri).then();
		}
	}

	private RequestSpecification setSSLConfig() throws FileNotFoundException {
		RequestSpecification request = RestAssured.given().config(RestAssured.config().sslConfig(new SSLConfig().with()
				.trustStoreType("JKS").and().trustStore(ResourceUtils.getFile("classpath:certs/apix.jks"), "njclabs")));
		return request;
	}

	public void createURI(String serviceName) {
		this.uri = serviceName;
	}

	public void addEndpoint(String endpoint) {
		this.uri = uri.concat(endpoint);
	}

	public void expectedResponse(int responseCode) {
		response.statusCode(responseCode);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void validateResponse(String filePath) throws IOException {
		ObjectMapper objectmapper = new ObjectMapper();
		Map<String, String> map = objectmapper.readValue(HelperUtil.getJsonStringFromPath(filePath), Map.class);

		for (Map.Entry<String, String> entrySet : map.entrySet()) {
			String key = entrySet.getKey();
			final String value = entrySet.getValue();
			response.body(key, new ResponseAwareMatcher() {
				public Matcher matcher(ResponseBody response) throws Exception {
					return equalTo(value);
				}
			});
		}
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setRequestBody(String filePath) throws IOException {
		String jsonString = HelperUtil.getJsonStringFromPath(filePath);
		if (jsonString != null && jsonString.contains(RANDOM)) {
			jsonString = getManipulatedString(jsonString);
		}
		this.body = jsonString;
	}

	@SuppressWarnings("unchecked")
	private String getManipulatedString(String jsonString) throws IOException {
		JSONObject jsonObject = new JSONObject();
		ObjectMapper objectmapper = new ObjectMapper();

		Map<String, String> map = objectmapper.readValue(jsonString, Map.class);
		for (Map.Entry<String, String> entrySet : map.entrySet()) {
			String value = entrySet.getValue();
			if (value.contains(RANDOM)) {
				entrySet.setValue(HelperUtil.getRandomValue(value));
			}
			jsonObject.put(entrySet.getKey(), entrySet.getValue());
		}
		return jsonObject.toJSONString();
	}

}
