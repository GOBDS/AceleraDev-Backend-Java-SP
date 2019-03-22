package challenge;

import java.util.List;

;

public interface RestaurantService {

	NeighborhoodRedis findInNeighborhood(double x, double y);

	List<RestaurantMongo> getAllRestaurants();

}
