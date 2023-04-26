package med.voll.api.model.entity.medicamento.dto;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoMedicamento(
        @NotNull
        Long id,
        String nome,
        String laboratorio,
        double miligramas) {
}
