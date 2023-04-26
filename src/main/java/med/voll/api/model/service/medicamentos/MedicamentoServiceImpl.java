package med.voll.api.model.service.medicamentos;


import med.voll.api.model.entity.medicamento.Medicamento;
import med.voll.api.model.entity.medicamento.dto.DadosAtualizacaoMedicamento;
import med.voll.api.model.repository.MedicamentoRepository;
import med.voll.api.model.repository.MedicoRepository;
import med.voll.api.model.service.impl.GenericCrudServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class MedicamentoServiceImpl extends GenericCrudServiceImpl<Medicamento>
        implements MedicamentoService {


    @Override
    @Transactional
    public Medicamento atualizarInformacoes(DadosAtualizacaoMedicamento dados) {
        var medicamento = this.getReferenceById(dados.id());
        medicamento.atualizarInformacoes(dados);
        return medicamento;
    }

    @Override
    public Page<Medicamento> findAllAtivo(Pageable paginacao) {
        return ((MedicamentoRepository) repository).findAllByDisponivelTrue(paginacao);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        var medicamento = this.getReferenceById(id);
        medicamento.tornarIndisponivel();
    }
}
