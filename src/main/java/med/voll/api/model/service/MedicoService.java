package med.voll.api.model.service;

import med.voll.api.model.entity.medico.DadosAtualizacaoMedico;
import med.voll.api.model.entity.medico.Especialidade;
import med.voll.api.model.entity.medico.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface MedicoService extends GenericCrudService<Medico> {

    Medico atualizarInformacoes(DadosAtualizacaoMedico dados);

    Page<Medico> findAllByAtivoTrue(Pageable paginacao);

    Medico escolherMedicoAleatorioLivreNaData(Especialidade especialidade, LocalDateTime data);
}
