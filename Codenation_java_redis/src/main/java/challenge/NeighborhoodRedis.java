package challenge;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

/**
 * Classe para mapear o bairro no Redis
 *
 */
@RedisHash("neighborhood")
public class NeighborhoodRedis {

    @Id
    private String id;
    private String name;
    private List<RestaurantRedis> restaurants;

    public NeighborhoodRedis() {
    }

    public NeighborhoodRedis(String id, String name, List<RestaurantRedis> restaurants) {
        this.id = id;
        this.name = name;
        this.restaurants = restaurants;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RestaurantRedis> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<RestaurantRedis> restaurants) {
        this.restaurants = restaurants;
    }
}
