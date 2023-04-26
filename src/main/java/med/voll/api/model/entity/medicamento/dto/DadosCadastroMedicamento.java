package med.voll.api.model.entity.medicamento.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroMedicamento(

        @JsonProperty("id_farmacia")
        @NotNull(message = "{farmacia.obrigatorio}")
        Long idFarmacia,

        @NotBlank(message = "{nome.obrigatorio}")
        String nome,

        @NotBlank(message = "{laboratorio.obrigatorio}")
        String laboratorio,

        @NotNull(message = "{disponibilidade.obrigatorio}")
        boolean disponivel,

        @DecimalMin(value = "0")
        double miligramas) {
}
