package med.voll.api.model.service.consultas;


import med.voll.api.exceptions.ValidacaoException;
import med.voll.api.model.service.consultas.validacoes.agendamento.ValidadorAgendamentoConsulta;
import med.voll.api.model.entity.consulta.Consulta;
import med.voll.api.model.entity.consulta.DadosAgendamentoConsulta;
import med.voll.api.model.entity.consulta.DadosCancelamentoConsulta;
import med.voll.api.model.entity.consulta.DadosDetalhamentoConsulta;
import med.voll.api.model.entity.medico.Medico;
import med.voll.api.model.service.consultas.ConsultaService;
import med.voll.api.model.service.MedicoService;
import med.voll.api.model.service.PacienteService;
import med.voll.api.model.service.consultas.validacoes.cancelamento.ValidadorCancelamentoConsulta;
import med.voll.api.model.service.impl.GenericCrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class ConsultaServiceImpl extends GenericCrudServiceImpl<Consulta>
        implements ConsultaService {

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private List<ValidadorAgendamentoConsulta> validadoresAgendamento;

    @Autowired
    private List<ValidadorCancelamentoConsulta> validadoresCancelamento;

    @Override
    @Transactional
    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {
        var paciente = pacienteService.getReferenceById(dados.idPaciente());
        var medico = escolherMedico(dados);

        validadoresAgendamento.forEach(val -> val.validar(dados));

        var consulta = new Consulta(null, medico, paciente, dados.data(), null);
        repository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);
    }

    @Override
    @Transactional
    public void cancelar(DadosCancelamentoConsulta dados) {
        var consulta = this.getReferenceById(dados.idConsulta());
        validadoresCancelamento.forEach(val -> val.validar(dados));
        consulta.cancelar(dados.motivo());
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() != null) {
            return medicoService.getReferenceById(dados.idMedico());
        }

        if (dados.especialidade() == null) {
            throw new ValidacaoException("Especialidade é obrigatória quando médico não for escolhido");
        }

        var medico = medicoService.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
        if(medico == null){
            throw new ValidacaoException("Não existe médico disponível nessa data");
        }
        return medico;
    }
}
