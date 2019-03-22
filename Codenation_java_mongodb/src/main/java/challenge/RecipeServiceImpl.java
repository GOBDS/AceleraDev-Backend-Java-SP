package challenge;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class RecipeServiceImpl implements RecipeService {

	private RecipeRepository repository;

	private MongoTemplate mongoTemplate;

	@Autowired
	public RecipeServiceImpl(RecipeRepository repository,  MongoTemplate mongoTemplate){
		this.repository = repository;
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public Recipe save(Recipe recipe) {
		final String uuid = UUID.randomUUID().toString().replace("-", "");
		recipe.setId(uuid);
		return repository.insert(recipe);
	}

	@Override
	public void update(String id, Recipe recipe) {
		Recipe updatedRecipe = repository.findById(id).orElseThrow(NullPointerException::new);
		updatedRecipe.setTitle(recipe.getTitle());
		updatedRecipe.setDescription(recipe.getDescription());
		updatedRecipe.setIngredients(recipe.getIngredients());
		repository.save(updatedRecipe);
	}

	@Override
	public void delete(String id) {
		repository.deleteById(id);
	}

	@Override
	public Recipe get(String id) {
		return repository.findById(id).orElseThrow(NullPointerException::new);
	}

	@Override
	public List<Recipe> listByIngredient(String ingredient) {
		Query query = new Query();
		query.addCriteria(Criteria.where("ingredients").regex(Pattern.compile(ingredient, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
		query.with(new Sort(Sort.Direction.ASC,"title"));
		return mongoTemplate.find(query,Recipe.class);
	}

	@Override
	public List<Recipe> search(String search) {
		Query query = new Query();
		Criteria title = Criteria.where("title").regex(Pattern.compile(search, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE));
		Criteria description = Criteria.where("description").regex(Pattern.compile(search, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE));
		query.addCriteria(new Criteria().orOperator(title,description));
		query.with(new Sort(Sort.Direction.ASC,"title"));
		return mongoTemplate.find(query,Recipe.class);
	}

	@Override
	public void like(String id, String userId) {
		Recipe recipe = repository.findById(id).orElseThrow(NullPointerException::new);
		List<String> likes = null == recipe.getLikes() ? new ArrayList<>() :  recipe.getLikes();
		likes.add(userId);
		recipe.setLikes(likes);
		repository.save(recipe);
	}

	@Override
	public void unlike(String id, String userId) {
		Recipe recipe = repository.findById(id).orElseThrow(NullPointerException::new);
		List<String> likes = recipe.getLikes();
		likes.remove(userId);
		recipe.setLikes(likes);
		repository.save(recipe);
	}

	@Override
	public RecipeComment addComment(String id, RecipeComment comment) {
		comment.setId(UUID.randomUUID().toString().replace("-", ""));
		Recipe recipe = repository.findById(id).orElseThrow(NullPointerException::new);
		List<RecipeComment> comments = null == recipe.getComments() ? new ArrayList<>() :  recipe.getComments();
		comments.add(comment);
		recipe.setComments(comments);
		repository.save(recipe);
		return comment;
	}

	@Override
	public void updateComment(String id, String commentId, RecipeComment comment) {
		comment.setId(commentId);
		Recipe recipe = repository.findById(id).orElseThrow(NullPointerException::new);
		List<RecipeComment> comments = recipe.getComments();
		int index = comments.indexOf(comment);
		comments.get(index).setComment(comment.getComment());
		recipe.setComments(comments);
		repository.save(recipe);
	}

	@Override
	public void deleteComment(String id, String commentId) {
		RecipeComment comment = new RecipeComment();
		comment.setId(commentId);
		Recipe recipe = repository.findById(id).orElseThrow(NullPointerException::new);
		List<RecipeComment> comments = recipe.getComments();
		comments.remove(comment);
		recipe.setComments(comments);
		repository.save(recipe);
	}

}
