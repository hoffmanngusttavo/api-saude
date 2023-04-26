package med.voll.api.model.service.medicamentos;

import med.voll.api.model.entity.medicamento.Bairro;
import med.voll.api.model.entity.medicamento.dto.DadosAtualizacaoBairro;
import med.voll.api.model.entity.medico.Medico;
import med.voll.api.model.service.GenericCrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BairroService extends GenericCrudService<Bairro> {


    Bairro atualizarInformacoes(DadosAtualizacaoBairro dados);

    Page<Bairro> findAll(Pageable paginacao);
}
