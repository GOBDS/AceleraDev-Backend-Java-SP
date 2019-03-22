package challenge;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.redis.core.index.GeoIndexed;

import java.util.List;

/**
 * Classe para mapear o bairro no MongoDB
 *
 */
@Document(collection = "neighborhood")
public class NeighborhoodMongo {

    @Id
    private String id;

    private String name;

    @GeoIndexed
    private GeoJsonPolygon geometry;

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

    public GeoJsonPolygon getGeometry() {
        return geometry;
    }

    public void setGeometry(GeoJsonPolygon geometry) {
        this.geometry = geometry;
    }
}
