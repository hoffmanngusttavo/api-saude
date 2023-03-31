package med.voll.api.model.service;

import med.voll.api.model.entity.paciente.DadosListagemPaciente;
import med.voll.api.model.entity.paciente.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PacienteService extends GenericCrudService<Paciente> {

    Page<Paciente> findAll(Pageable paginacao);
}
