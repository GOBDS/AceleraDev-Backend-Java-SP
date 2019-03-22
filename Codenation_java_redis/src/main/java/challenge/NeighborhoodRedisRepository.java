package challenge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
//public interface NeighborhoodRedisRepository extends CrudRepository<NeighborhoodRedis, String> {
public class NeighborhoodRedisRepository {

    private static final String KEY_NAME = "neighborhood:";

    private RedisTemplate<String, NeighborhoodRedis> redisTemplate;

    @Autowired
    public NeighborhoodRedisRepository(RedisTemplate<String, NeighborhoodRedis> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    public Optional<NeighborhoodRedis> findById(String Key){
        return Optional.ofNullable(redisTemplate.opsForValue().get(KEY_NAME + Key));
    }

    public NeighborhoodRedis save(NeighborhoodRedis neighborhood){
        redisTemplate.opsForValue().set(KEY_NAME + neighborhood.getId(), neighborhood);
        return findById(neighborhood.getId()).get();
    }

}
