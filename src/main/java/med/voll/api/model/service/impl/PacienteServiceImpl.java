package med.voll.api.model.service.impl;


import med.voll.api.model.entity.paciente.DadosAtualizacaoPaciente;
import med.voll.api.model.entity.paciente.Paciente;
import med.voll.api.model.repository.PacienteRepository;
import med.voll.api.model.service.PacienteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class PacienteServiceImpl extends GenericCrudServiceImpl<Paciente>
        implements PacienteService {


    @Override
    public Page<Paciente> findAllByAtivoTrue(Pageable paginacao) {
        return ((PacienteRepository) repository).findAllByAtivoTrue(paginacao);
    }

    @Override
    @Transactional
    public void atualizarInformacoes(DadosAtualizacaoPaciente dados) {
        var paciente = this.findById(dados.id());
        paciente.atualizarInformacoes(dados);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        var paciente = repository.getReferenceById(id);
        paciente.inativar();
    }
}
