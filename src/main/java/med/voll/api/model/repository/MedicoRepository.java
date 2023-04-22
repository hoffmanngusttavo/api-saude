package med.voll.api.model.repository;

import med.voll.api.model.entity.medico.Medico;
import med.voll.api.model.repository.generic.GenericCrudRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicoRepository extends GenericCrudRepository<Medico> {

    Page<Medico> findAllByAtivoTrue(Pageable paginacao);

}
