package med.voll.api.model.service.medicamentos;


import med.voll.api.model.entity.medicamento.Medicamento;
import med.voll.api.model.entity.medicamento.dto.DadosAtualizacaoMedicamento;
import med.voll.api.model.repository.MedicamentoRepository;
import med.voll.api.model.service.impl.GenericCrudServiceImpl;
import org.apache.commons.lang3.StringUtils;
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
    public Page<Medicamento> findAllByDisponivelTrue(Pageable paginacao) {
        return ((MedicamentoRepository) repository).findAllByDisponivelTrue(paginacao);
    }

    @Override
    public Page<Medicamento> findByMedicamentosBairro(Long idBairro, String medicamento, Pageable paginacao) {
        medicamento = StringUtils.isNotBlank(medicamento) ? medicamento : null;
        return ((MedicamentoRepository) repository).findByMedicamentosBairro(idBairro, medicamento, paginacao);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        var medicamento = this.getReferenceById(id);
        medicamento.tornarIndisponivel();
    }
}
