package med.voll.api.model.repository;

import med.voll.api.model.entity.medico.Medico;
import med.voll.api.model.entity.paciente.Paciente;
import med.voll.api.model.repository.generic.GenericCrudRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends GenericCrudRepository<Paciente> {
    Page<Paciente> findAllByAtivoTrue(Pageable paginacao);
}
