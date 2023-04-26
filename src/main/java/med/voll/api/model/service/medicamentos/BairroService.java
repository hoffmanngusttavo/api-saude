package med.voll.api.model.service.medicamentos;

import med.voll.api.model.entity.medicamento.Bairro;
import med.voll.api.model.entity.medicamento.dto.DadosAtualizacaoBairro;
import med.voll.api.model.service.GenericCrudService;

public interface BairroService extends GenericCrudService<Bairro> {


    Bairro atualizarInformacoes(DadosAtualizacaoBairro dados);


}
