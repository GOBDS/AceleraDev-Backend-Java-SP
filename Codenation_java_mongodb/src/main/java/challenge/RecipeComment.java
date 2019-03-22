package challenge;

import org.springframework.boot.context.properties.DeprecatedConfigurationProperty;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

/**
 * Classe para mapear o comentario da receita no MongoDB
 *
 */
public class RecipeComment {

    private String id;
    private String comment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RecipeComment)) return false;
        RecipeComment that = (RecipeComment) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
