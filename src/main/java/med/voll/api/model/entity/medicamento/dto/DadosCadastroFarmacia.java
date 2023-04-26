package med.voll.api.model.entity.medicamento.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record DadosCadastroFarmacia(

        @JsonProperty("id_bairro")
        @NotNull(message = "{bairro.obrigatorio}")
        Long idBairro,

        @NotBlank(message = "{nome.obrigatorio}")
        String nome,

        @NotBlank(message = "{endereco.obrigatorio}")
        String endereco,

        @NotNull(message = "{telefone.obrigatorio}")
        String telefone,

        @JsonProperty("horario_inicio_funcionamento")
        @NotNull(message = "{horario.inicio.obrigatorio}")
        @JsonFormat(pattern = "HH:mm")
        LocalTime horarioInicioFuncionamento,

        @JsonProperty("horario_termino_funcionamento")
        @NotNull(message = "{horario.termino.obrigatorio}")
        @JsonFormat(pattern = "HH:mm")
        LocalTime horarioTerminoFuncionamento ) {
}
