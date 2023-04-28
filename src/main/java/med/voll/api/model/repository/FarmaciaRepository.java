package med.voll.api.model.repository;

import med.voll.api.model.entity.medicamento.Farmacia;
import med.voll.api.model.repository.generic.GenericCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FarmaciaRepository extends GenericCrudRepository<Farmacia> {

    Optional<Farmacia> findByIdExterno(String idExterno);
}
