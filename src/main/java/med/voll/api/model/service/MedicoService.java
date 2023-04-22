package med.voll.api.model.service;

import med.voll.api.model.entity.medico.DadosAtualizacaoMedico;
import med.voll.api.model.entity.medico.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

public interface MedicoService extends GenericCrudService<Medico> {

    void atualizarInformacoes(DadosAtualizacaoMedico dados);

    Page<Medico> findAllByAtivoTrue(Pageable paginacao);
}
