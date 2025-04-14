package domain.repository;

import domain.models.Enclosure;
import domain.vo.EnclosureId;

import java.util.List;
import java.util.Optional;

public interface EnclosureRepository {

    Optional<Enclosure> findById(EnclosureId enclosureId);
    List<Enclosure> findAll();
    Enclosure save(Enclosure enclosure);
    void delete(EnclosureId enclosureId);

}
