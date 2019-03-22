package challenge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {

	Logger logger = LoggerFactory.getLogger(RestaurantServiceImpl.class);

	RestaurantRepository restaurantRepository;
	NeighborhoodRepository neighborhoodRepository;
	NeighborhoodRedisRepository redisRepository;

	@Autowired
	public RestaurantServiceImpl(RestaurantRepository restaurantRepository, NeighborhoodRepository neighborhoodRepository, NeighborhoodRedisRepository redisRepository){
		this.restaurantRepository = restaurantRepository;
		this.neighborhoodRepository = neighborhoodRepository;
		this.redisRepository = redisRepository;
	}

	@Override
	public NeighborhoodRedis findInNeighborhood(double x, double y) {
		NeighborhoodMongo neighborhood = neighborhoodRepository.findNeighborhoodByCoordinates(new GeoJsonPoint(x, y));
		logger.info("Neighboorhood: " + neighborhood.getName());
		Optional<NeighborhoodRedis> rNeighborhood = redisRepository.findById(neighborhood.getId());
		if(rNeighborhood.isPresent()){
			logger.info("Redis");
			return rNeighborhood.get();
		}else {
			logger.info("NotRedis");
			List<RestaurantRedis> restaurantsByNeighbor;
			restaurantsByNeighbor = restaurantRepository.findByLocationWithinOrderByName(neighborhood.getGeometry()).stream().map(item -> new RestaurantRedis(item)).collect(Collectors.toList());
			return redisRepository.save(new NeighborhoodRedis(neighborhood.getId(), neighborhood.getName(), restaurantsByNeighbor));
		}
	}

	public List<RestaurantMongo> getAllRestaurants() {
		return restaurantRepository.findAll();
	}
}
