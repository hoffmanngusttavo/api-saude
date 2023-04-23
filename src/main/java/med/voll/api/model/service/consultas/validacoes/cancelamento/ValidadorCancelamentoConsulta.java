package med.voll.api.model.service.consultas.validacoes.cancelamento;

import med.voll.api.model.entity.consulta.DadosCancelamentoConsulta;

public interface ValidadorCancelamentoConsulta {

    void validar(DadosCancelamentoConsulta dados);
}
