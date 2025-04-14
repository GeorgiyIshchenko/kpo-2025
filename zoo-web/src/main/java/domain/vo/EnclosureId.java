package domain.vo;

import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

@Getter
public final class EnclosureId {

    private final UUID value;

    public EnclosureId(UUID value) {
        if (value == null) {
            throw new IllegalArgumentException("EnclosureID cannot be null");
        }
        this.value = value;
    }

    public static EnclosureId createNew() {
        return new EnclosureId(UUID.randomUUID());
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
        EnclosureId other = (EnclosureId) obj;
        return Objects.equals(value, other.value);
    }

}
