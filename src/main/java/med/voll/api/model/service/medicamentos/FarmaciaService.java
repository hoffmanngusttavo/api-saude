package med.voll.api.model.service.medicamentos;

import med.voll.api.model.entity.medicamento.Farmacia;
import med.voll.api.model.entity.medicamento.dto.DadosAtualizacaoFarmacia;
import med.voll.api.model.entity.medicamento.dto.DadosIntegracaoUnidadeSaudePrefeitura;
import med.voll.api.model.service.GenericCrudService;

import java.util.Optional;

public interface FarmaciaService extends GenericCrudService<Farmacia> {


    Farmacia atualizarInformacoes(DadosAtualizacaoFarmacia dados);

    Optional<Farmacia> findByIdExterno(String idExterno);

    Farmacia criarNovaUnidadeSaude(DadosIntegracaoUnidadeSaudePrefeitura dadosIntegracaoUnidadeSaudePrefeitura);
}
