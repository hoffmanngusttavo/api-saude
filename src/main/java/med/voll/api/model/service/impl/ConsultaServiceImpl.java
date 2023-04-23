package med.voll.api.model.service.impl;


import med.voll.api.exceptions.ValidacaoException;
import med.voll.api.model.component.agenda.ValidadorAgendamentoConsulta;
import med.voll.api.model.entity.consulta.Consulta;
import med.voll.api.model.entity.consulta.DadosAgendamentoConsulta;
import med.voll.api.model.entity.consulta.DadosCancelamentoConsulta;
import med.voll.api.model.entity.medico.Medico;
import med.voll.api.model.service.ConsultaService;
import med.voll.api.model.service.MedicoService;
import med.voll.api.model.service.PacienteService;
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
    private List<ValidadorAgendamentoConsulta> validadores;

    @Override
    @Transactional
    public void agendar(DadosAgendamentoConsulta dados) {
        var paciente = pacienteService.getReferenceById(dados.idPaciente());
        var medico = escolherMedico(dados);

        validadores.forEach(val -> val.validar(dados));

        var consulta = new Consulta(null, medico, paciente, dados.data(), null);
        repository.save(consulta);
    }

    @Override
    @Transactional
    public void cancelar(DadosCancelamentoConsulta dados) {
        var consulta = repository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() != null) {
            return medicoService.getReferenceById(dados.idMedico());
        }

        if (dados.especialidade() == null) {
            throw new ValidacaoException("Especialidade é obrigatória quando médico não for escolhido");
        }

        return medicoService.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }
}
