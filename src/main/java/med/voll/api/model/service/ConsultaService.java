package med.voll.api.model.service;

import med.voll.api.model.entity.consulta.Consulta;
import med.voll.api.model.entity.consulta.DadosAgendamentoConsulta;
import med.voll.api.model.entity.consulta.DadosCancelamentoConsulta;

public interface ConsultaService extends GenericCrudService<Consulta> {


    void agendar(DadosAgendamentoConsulta dados);

    void cancelar(DadosCancelamentoConsulta dados);
}
