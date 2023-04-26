package med.voll.api.model.entity.medicamento.dto;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroBairro(
        @NotBlank(message = "{nome.obrigatorio}")
        String nome
        ) {
}
