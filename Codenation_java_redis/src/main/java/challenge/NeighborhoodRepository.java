package challenge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJson;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class NeighborhoodRepository {

    MongoTemplate mongoTemplate;

    @Autowired
    public NeighborhoodRepository(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }

    public NeighborhoodMongo findNeighborhoodByCoordinates(GeoJsonPoint location){
        Query query = new Query();
        query.addCriteria(Criteria.where("geometry").intersects(location));
        return mongoTemplate.findOne(query,NeighborhoodMongo.class);
    }
}
