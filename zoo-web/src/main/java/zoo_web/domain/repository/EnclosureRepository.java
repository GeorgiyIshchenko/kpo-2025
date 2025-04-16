package zoo_web.domain.repository;

import zoo_web.domain.models.Enclosure;
import zoo_web.domain.vo.EnclosureId;

import java.util.List;
import java.util.Optional;

public interface EnclosureRepository {

    Optional<Enclosure> findById(EnclosureId enclosureId);
    List<Enclosure> findAll();
    Enclosure save(Enclosure enclosure);
    void delete(EnclosureId enclosureId);
    void deleteAll();

}
