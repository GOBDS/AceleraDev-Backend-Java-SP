package challenge;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

	private RecipeService service;

	@Autowired
	public RecipeController(RecipeService service){
		this.service = service;
	}

	@PostMapping
	@ResponseBody
	public Recipe save(@RequestBody Recipe recipe) {
		return service.save(recipe);
	}

	@PutMapping("/{recipeId}")
	public void update(@NotNull  @PathVariable String recipeId, @RequestBody Recipe recipe) {
		service.update(recipeId, recipe);
	}

	@DeleteMapping("/{recipeId}")
	public void delete(@NotNull @PathVariable String recipeId) {
		service.delete(recipeId);
	}

	@GetMapping("/{recipeId}")
	public Recipe get(@NotNull @PathVariable String recipeId) {
		return service.get(recipeId);
	}

	@GetMapping("/ingredient")
	public List<Recipe> listByIngredient(@RequestParam(name = "ingredient") String ingredient) {
		return service.listByIngredient(ingredient);
	}

	@GetMapping("/search")
	public List<Recipe> search(@RequestParam(name = "search") String search) {
		return service.search(search);
	}

	@PostMapping("/{recipeId}/like/{userId}")
	public void like(@NotNull @PathVariable String recipeId, @NotNull @PathVariable String userId) {
		service.like(recipeId, userId);
	}

	@DeleteMapping("/{recipeId}/like/{userId}")
	public void unlike(@NotNull @PathVariable String recipeId, @NotNull @PathVariable String userId) {
		service.unlike(recipeId, userId);
	}

	@PostMapping("/{recipeId}/comment")
	@ResponseBody
	public RecipeComment addComment(@NotNull @PathVariable String recipeId, @RequestBody RecipeComment recipeComment) {
		return service.addComment(recipeId, recipeComment);
	}

	@PutMapping("/{recipeId}/comment/{commentId}")
	public void updateComment(@NotNull @PathVariable String recipeId, @NotNull @PathVariable String commentId, @RequestBody RecipeComment recipeComment) {
		service.updateComment(recipeId, commentId, recipeComment);
	}

	@DeleteMapping("/{recipeId}/comment/{commentId}")
	public void deleteComment(@NotNull @PathVariable String recipeId, @PathVariable String commentId) {
		service.deleteComment(recipeId, commentId);
	}

}
