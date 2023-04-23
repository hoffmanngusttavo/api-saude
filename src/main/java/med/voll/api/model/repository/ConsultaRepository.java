package med.voll.api.model.repository;

import med.voll.api.model.entity.consulta.Consulta;
import med.voll.api.model.repository.generic.GenericCrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ConsultaRepository extends GenericCrudRepository<Consulta> {


    boolean existsByPacienteIdAndDataBetween(Long idPaciente, LocalDateTime primeiroHorario, LocalDateTime ultimoHorario);

    boolean existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(Long idMedico, LocalDateTime data);


}
