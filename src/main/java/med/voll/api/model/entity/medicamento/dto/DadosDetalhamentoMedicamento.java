package med.voll.api.model.entity.medicamento.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import med.voll.api.model.entity.medicamento.Medicamento;

public record DadosDetalhamentoMedicamento(Long id,
                                           String nome,
                                           String laboratorio,
                                           boolean disponivel,
                                           double miligramas,
                                           @JsonProperty("id_farmacia")
                                           Long idFarmacia) {

    public DadosDetalhamentoMedicamento(Medicamento medicamento){
        this(medicamento.getId(), medicamento.getNome(), medicamento.getLaboratorio(),
                medicamento.getDisponivel(), medicamento.getMiligramas(),
                medicamento.getFarmacia().getId());
    }


}
