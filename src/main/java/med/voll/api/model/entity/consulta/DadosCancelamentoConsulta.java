package med.voll.api.model.entity.consulta;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record DadosCancelamentoConsulta(
        @NotNull
        @JsonProperty("id_consulta")
        Long idConsulta,

        @NotNull
        MotivoCancelamento motivo) {
}
