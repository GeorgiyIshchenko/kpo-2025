package zoo_web.domain.vo;

import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

@Getter
public final class AnimalId {

    private final UUID value;

    public AnimalId(UUID value) {
        if (value == null) {
            throw new IllegalArgumentException("AnimalId cannot be null");
        }
        this.value = value;
    }

    public static AnimalId createNew() {
        return new AnimalId(UUID.randomUUID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AnimalId other = (AnimalId) obj;
        return Objects.equals(value, other.value);
    }

}
