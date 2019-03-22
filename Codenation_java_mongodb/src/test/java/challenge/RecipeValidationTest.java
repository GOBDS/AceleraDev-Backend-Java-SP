package challenge;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")

public class RecipeValidationTest {
	
	@Autowired
	private TestRestTemplate restTemplate;

	private static final String SAVE_BODY = "{\"title\": \"Bolo de chocolate\",\"description\": \"Bolo de chocolate\",\"ingredients\": [\"ovo\",\"chocolate\"]}";
	private static final String UPDATE_BODY = "{\"title\": \"Bolo de chocolate\",\"description\": \"Bolo de chocolate caseiro\",\"ingredients\": [\"ovo\",\"chocolate\"]}";
	private static final String COMMENT_BODY = "{\"comment\": \"Muito gostoso!\"}";
	private static final String COMMENT_UPDATE_BODY = "{\"comment\": \"Muito gostoso MESMO!\"}";


	private final HttpHeaders httpHeaders;

	private ResponseEntity<Recipe> response;
	
	public RecipeValidationTest() {
		httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
	}

	@Before
	public void init(){
		response = restTemplate.exchange("/recipe", HttpMethod.POST, new HttpEntity<>(SAVE_BODY, httpHeaders), Recipe.class);
	}

	@Test
	public void save() {
		ResponseEntity<Recipe> response = restTemplate.exchange("/recipe", HttpMethod.POST, new HttpEntity<>(SAVE_BODY, httpHeaders), Recipe.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void update() {		
		ResponseEntity<Void> updateResponse = restTemplate.exchange("/recipe/" + response.getBody().getId(), HttpMethod.PUT, new HttpEntity<>(UPDATE_BODY, httpHeaders), Void.class);
		assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void delete() {		
		ResponseEntity<Void> deleteResponse = restTemplate.exchange("/recipe/" + response.getBody().getId(), HttpMethod.DELETE, null, Void.class);
		assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void get() {		
		ResponseEntity<Recipe> getResponse = restTemplate.exchange("/recipe/" + response.getBody().getId(), HttpMethod.GET, null, Recipe.class);
		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void listByIngredient() {
		ResponseEntity<Void> response = restTemplate.exchange("/recipe/ingredient?ingredient=ovo", HttpMethod.GET, null, Void.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void search() {		
		ResponseEntity<Void> response = restTemplate.exchange("/recipe/search?search=bolo", HttpMethod.GET, null, Void.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void like() {		
		ResponseEntity<Void> likeResponse = restTemplate.exchange("/recipe/" + response.getBody().getId() + "/like/1122", HttpMethod.POST, null, Void.class);
		assertThat(likeResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	public void unlike() {
		restTemplate.exchange("/recipe/" + response.getBody().getId() + "/like/1122", HttpMethod.POST, null, Void.class);
		ResponseEntity<Void> unlikeResponse = restTemplate.exchange("/recipe/" + response.getBody().getId() + "/like/1122", HttpMethod.DELETE, null, Void.class);
		assertThat(unlikeResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	public void addComment() {		
		ResponseEntity<Void> commentResponse = restTemplate.exchange("/recipe/" + response.getBody().getId() + "/comment", HttpMethod.POST, new HttpEntity<>(COMMENT_BODY, httpHeaders), Void.class);
		assertThat(commentResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	public void updateComment() {
		restTemplate.exchange("/recipe/" + response.getBody().getId() + "/comment", HttpMethod.POST, new HttpEntity<>(COMMENT_BODY, httpHeaders), Void.class);
		ResponseEntity<Recipe> getResponse = restTemplate.exchange("/recipe/" + response.getBody().getId(), HttpMethod.GET, null, Recipe.class);
		ResponseEntity<Void> updateResponse = restTemplate.exchange("/recipe/" + response.getBody().getId() + "/comment/" + getResponse.getBody().getComments().get(0).getId(), HttpMethod.PUT, new HttpEntity<>(COMMENT_UPDATE_BODY, httpHeaders), Void.class);
		assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	public void deleteComment() {
		restTemplate.exchange("/recipe/" + response.getBody().getId() + "/comment", HttpMethod.POST, new HttpEntity<>(COMMENT_BODY, httpHeaders), Void.class);
		ResponseEntity<Recipe> getResponse = restTemplate.exchange("/recipe/" + response.getBody().getId(), HttpMethod.GET, null, Recipe.class);
		ResponseEntity<Void> deleteResponse = restTemplate.exchange("/recipe/" + response.getBody().getId() + "/comment/" + getResponse.getBody().getComments().get(0).getId(), HttpMethod.DELETE, null, Void.class);
		assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

}
