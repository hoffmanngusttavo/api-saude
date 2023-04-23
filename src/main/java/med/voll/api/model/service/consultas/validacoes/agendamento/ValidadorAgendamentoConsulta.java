package med.voll.api.model.service.consultas.validacoes.agendamento;

import med.voll.api.model.entity.consulta.DadosAgendamentoConsulta;

public interface ValidadorAgendamentoConsulta {

    void validar(DadosAgendamentoConsulta dados);
}
