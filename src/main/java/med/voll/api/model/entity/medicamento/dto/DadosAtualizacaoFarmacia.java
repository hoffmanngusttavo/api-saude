package med.voll.api.model.entity.medicamento.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record DadosAtualizacaoFarmacia(
        @NotNull
        Long id,
        String nome,

        String endereco,

        String telefone,

        @JsonProperty("horario_inicio_funcionamento")
        @JsonFormat(pattern = "HH:mm")
        LocalTime horarioInicioFuncionamento,

        @JsonProperty("horario_termino_funcionamento")
        @JsonFormat(pattern = "HH:mm")
        LocalTime horarioTerminoFuncionamento) {
}
