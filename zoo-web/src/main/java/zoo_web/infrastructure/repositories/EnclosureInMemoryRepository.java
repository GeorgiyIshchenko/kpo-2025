package zoo_web.infrastructure.repositories;

import zoo_web.domain.models.Enclosure;
import zoo_web.domain.repository.EnclosureRepository;
import zoo_web.domain.vo.EnclosureId;

import java.util.*;

public class EnclosureInMemoryRepository implements EnclosureRepository {

    private final Map<UUID, Enclosure> store = new HashMap<>();

    @Override
    public Optional<Enclosure> findById(EnclosureId id) {
        return Optional.ofNullable(store.get(id.getValue()));
    }

    @Override
    public List<Enclosure> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Enclosure save(Enclosure enclosure) {
        store.put(enclosure.getId().getValue(), enclosure);
        return enclosure;
    }

    @Override
    public void delete(EnclosureId id) {
        store.remove(id.getValue());
    }

    @Override
    public void deleteAll() {
        store.clear();
    }

}
