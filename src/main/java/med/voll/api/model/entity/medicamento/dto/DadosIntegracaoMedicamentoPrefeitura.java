package med.voll.api.model.entity.medicamento.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DadosIntegracaoMedicamentoPrefeitura(

        String uuid,
        String nome,
        String laboratorio,
        boolean disponivel,
        double miligramas,
        @JsonProperty("unidade_saude")
        DadosIntegracaoUnidadeSaudePrefeitura unidadeSaude) {

}
