package med.voll.api.model.entity.medicamento.dto;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoBairro(
        @NotNull
        Long id,
        @NotNull
        String nome
        ) {
}
