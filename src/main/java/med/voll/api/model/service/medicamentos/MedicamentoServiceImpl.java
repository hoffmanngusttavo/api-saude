package med.voll.api.model.service.medicamentos;


import med.voll.api.model.entity.medicamento.Medicamento;
import med.voll.api.model.entity.medicamento.dto.DadosAtualizacaoMedicamento;
import med.voll.api.model.entity.medicamento.dto.DadosIntegracaoMedicamentoPrefeitura;
import med.voll.api.model.entity.medicamento.dto.RetornoPaginacaoMedicamentoPrefeitura;
import med.voll.api.model.repository.MedicamentoRepository;
import med.voll.api.model.service.impl.GenericCrudServiceImpl;
import med.voll.api.web.client.PrefeituraWebRequestClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class MedicamentoServiceImpl extends GenericCrudServiceImpl<Medicamento>
        implements MedicamentoService {

    @Autowired
    private PrefeituraWebRequestClient prefeituraWebRequestClient;

    @Autowired
    private FarmaciaService farmaciaService;


    @Override
    @Transactional
    public Medicamento atualizarInformacoes(DadosAtualizacaoMedicamento dados) {
        var medicamento = this.getReferenceById(dados.id());
        medicamento.atualizarInformacoes(dados);
        return medicamento;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Medicamento> findAllByDisponivelTrue(Pageable paginacao) {
        return ((MedicamentoRepository) repository).findAllByDisponivelTrue(paginacao);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Medicamento> findByMedicamentosBairro(Long idBairro, String medicamento, Pageable paginacao) {
        medicamento = StringUtils.isNotBlank(medicamento) ? medicamento : null;
        return ((MedicamentoRepository) repository).findByMedicamentosBairro(idBairro, medicamento, paginacao);
    }

    @Override
    @Transactional
    public void importarMedicamentosPrefeitura() {
        var ontem = LocalDate.now().minusDays(1);
        int limit = 200;
        int page = 0;
        RetornoPaginacaoMedicamentoPrefeitura pages;
        do {
            pages = prefeituraWebRequestClient.getRequest(ontem, limit, page);
            associarMedicamentos(pages.getContent());
            page++;
        } while (page < pages.getTotalPages());
    }

    private void associarMedicamentos(List<DadosIntegracaoMedicamentoPrefeitura> content) {

        for (DadosIntegracaoMedicamentoPrefeitura dto : content) {

            if (farmaciaService.findByIdExterno(dto.unidadeSaude().uuid()).isEmpty()) {
                farmaciaService.criarNovaUnidadeSaude(dto.unidadeSaude());
            }
            var unidadeSaude = farmaciaService.findByIdExterno(dto.unidadeSaude().uuid()).get();

            var medicamento = ((MedicamentoRepository) repository).findByIdExterno(dto.uuid())
                    .orElse(new Medicamento());

            medicamento.integrarInformacoes(dto, unidadeSaude);

            this.save(medicamento);
        }
    }


    @Override
    @Transactional
    public void deleteById(Long id) {
        var medicamento = this.getReferenceById(id);
        medicamento.tornarIndisponivel();
    }
}
