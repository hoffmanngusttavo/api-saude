package med.voll.api.model.service.consultas.validacoes.agendamento;

import med.voll.api.exceptions.ValidacaoException;
import med.voll.api.model.entity.consulta.DadosAgendamentoConsulta;
import med.voll.api.model.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoComOutraConsultaNoMesmoHorario implements ValidadorAgendamentoConsulta {

    @Autowired
    private ConsultaRepository repository;

    public void validar(DadosAgendamentoConsulta dados) {

        if(repository.existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(dados.idMedico(), dados.data())){
            throw new ValidacaoException("Esse médico já está agendado em outra consulta no mesmo horário");
        }

    }
}
