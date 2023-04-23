package med.voll.api.model.service.consultas.validacoes.agendamento;

import med.voll.api.exceptions.ValidacaoException;
import med.voll.api.model.entity.consulta.DadosAgendamentoConsulta;
import med.voll.api.model.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteAtivo implements ValidadorAgendamentoConsulta {

    @Autowired
    private PacienteRepository pacienteRepository;

    public void validar(DadosAgendamentoConsulta dados) {

        if(!pacienteRepository.findAtivoById(dados.idPaciente()) ){
            throw new ValidacaoException("Paciente inativo");
        }

    }
}
