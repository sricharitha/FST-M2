package activities;

import java.util.Map;
import org.apache.commons.collections4.map.HashedMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;\
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@ExtendWith(PactConsumerTestExt.class)
class PactConsumerTest {
	
	Map<String, String> headers = new HashedMap<String, String>();
	String createUser = "/api/users";
	
	@Pact(provider = "UserProvider" , consumer = "UserConsumer")
	public RequestResponsePact createPact(PactDslWithProvider builder) {
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");
		
		DslPart bodySentCreateUser = new PactDslJsonBody()
				.numberType("id",1)
				.stringType("firstName","string")
				.stringType("lastName","string")
				.stringType("email","string");
		
		DslPart bodyReceivedCreateUser = new PactDslJsonBody()
			    .numberType("id", 1)
			    .stringType("firstName", "string")
			    .stringType("lastName", "string")
			    .stringType("email", "string");
		
		return builder.given("A request to create a user")
				.uponReceiving("A request to create a user")
				.path(createUser)
				.method("POST")
				.headers(headers)
				.body(bodySentCreateUser)
				.willRespondWith()
				.status(201)
				.body(bodyReceivedCreateUser)
				.toPact();
	}

	@Test
	@PactTestFor(providerName = "UserProvider" , port = "8080")
	void runTest() {
		RestAssured.baseURI = "http://localhost:8080";
		RequestSpecification rq = RestAssured.given().headers(headers).when();
		
		Map<String, Object> map = new HashedMap<String, Object>();
		map.put("id", 1);
		map.put("firstName", "Ankush");
		map.put("lastName", "Shetty");
		map.put("email", "ankush@mail.com");
		
		Response response = rq.body(map).post(createUser);
		System.out.println(response.getStatusCode());
	}

}