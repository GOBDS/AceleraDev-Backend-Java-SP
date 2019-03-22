package challenge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

	private final RestaurantService service;

	@Autowired
	public RestaurantController(RestaurantService service){
		this.service = service;
	}


	@GetMapping("/findInNeighborhood")
	public NeighborhoodRedis findInNeighborhood(@RequestParam(name = "x") Double latitude, @RequestParam(name = "y") Double longitude ) {
		return service.findInNeighborhood(latitude, longitude);
	}

	@GetMapping
	public List<RestaurantMongo> getAll(){
		return service.getAllRestaurants();
	}

}
