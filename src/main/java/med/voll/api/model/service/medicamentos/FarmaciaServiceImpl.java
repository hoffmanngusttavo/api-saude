package med.voll.api.model.service.medicamentos;


import med.voll.api.model.entity.medicamento.Bairro;
import med.voll.api.model.entity.medicamento.Farmacia;
import med.voll.api.model.entity.medicamento.dto.DadosAtualizacaoFarmacia;
import med.voll.api.model.entity.medicamento.dto.DadosIntegracaoUnidadeSaudePrefeitura;
import med.voll.api.model.repository.FarmaciaRepository;
import med.voll.api.model.service.impl.GenericCrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class FarmaciaServiceImpl extends GenericCrudServiceImpl<Farmacia>
        implements FarmaciaService {


    @Autowired
    private BairroService bairroService;


    @Override
    @Transactional
    public Farmacia atualizarInformacoes(DadosAtualizacaoFarmacia dados) {
        var farmacia = this.findById(dados.id());
        farmacia.atualizarInformacoes(dados);
        return farmacia;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Farmacia> findByIdExterno(String idExterno) {
        return ((FarmaciaRepository) repository).findByIdExterno(idExterno);
    }

    @Override
    @Transactional
    public Farmacia criarNovaUnidadeSaude(DadosIntegracaoUnidadeSaudePrefeitura dtoIntegracao) {
        if(bairroService.findByNome(dtoIntegracao.bairro()).isEmpty()){
            bairroService.save(new Bairro(dtoIntegracao.bairro()));
        }
        var bairro = bairroService.findByNome(dtoIntegracao.bairro()).get();
        var farmacia = new Farmacia(dtoIntegracao, bairro);
        return save(farmacia);
    }
}
