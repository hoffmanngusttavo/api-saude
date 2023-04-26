package med.voll.api.model.service.medicamentos;

import med.voll.api.model.entity.medicamento.Medicamento;
import med.voll.api.model.entity.medicamento.dto.DadosAtualizacaoMedicamento;
import med.voll.api.model.service.GenericCrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MedicamentoService extends GenericCrudService<Medicamento> {


    Medicamento atualizarInformacoes(DadosAtualizacaoMedicamento dados);

    Page<Medicamento> findAllAtivo(Pageable paginacao);
}
