package med.voll.api.model.repository;

import med.voll.api.model.entity.medico.Medico;
import med.voll.api.model.repository.generic.GenericCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicoRepository extends GenericCrudRepository<Medico> {
}
