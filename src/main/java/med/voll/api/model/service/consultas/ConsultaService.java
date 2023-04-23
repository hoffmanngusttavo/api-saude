package med.voll.api.model.service.consultas;

import med.voll.api.model.entity.consulta.Consulta;
import med.voll.api.model.entity.consulta.DadosAgendamentoConsulta;
import med.voll.api.model.entity.consulta.DadosCancelamentoConsulta;
import med.voll.api.model.entity.consulta.DadosDetalhamentoConsulta;
import med.voll.api.model.service.GenericCrudService;

public interface ConsultaService extends GenericCrudService<Consulta> {


    DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados);

    void cancelar(DadosCancelamentoConsulta dados);
}
