package med.voll.api.model.service.impl;


import med.voll.api.model.entity.medico.DadosAtualizacaoMedico;
import med.voll.api.model.entity.medico.Especialidade;
import med.voll.api.model.entity.medico.Medico;
import med.voll.api.model.repository.MedicoRepository;
import med.voll.api.model.service.MedicoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class MedicoServiceImpl extends GenericCrudServiceImpl<Medico>
        implements MedicoService {


    @Override
    @Transactional
    public Medico atualizarInformacoes(DadosAtualizacaoMedico dados) {
        var medico = this.findById(dados.id());
        medico.atualizarInformacoes(dados);
        return medico;
    }

    @Override
    public Page<Medico> findAllByAtivoTrue(Pageable paginacao) {
        return ((MedicoRepository) repository).findAllByAtivoTrue(paginacao);
    }

    @Override
    public Medico escolherMedicoAleatorioLivreNaData(Especialidade especialidade, LocalDateTime data) {
        return ((MedicoRepository) repository).escolherMedicoAleatorioLivreNaData(especialidade, data);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        var medico = repository.getReferenceById(id);
        medico.excluir();
    }
}
