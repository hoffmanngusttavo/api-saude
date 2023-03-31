package med.voll.api.model.repository;

import med.voll.api.model.entity.medico.Medico;
import med.voll.api.model.entity.paciente.Paciente;
import med.voll.api.model.repository.generic.GenericCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends GenericCrudRepository<Paciente> {
}
