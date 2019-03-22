package challenge;

import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends MongoRepository<RestaurantMongo, String> {

    List<RestaurantMongo> findByLocationWithinOrderByName(GeoJsonPolygon polygon);
}
