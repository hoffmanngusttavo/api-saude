package med.voll.api.model.entity.consulta;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.model.entity.medico.Especialidade;

import java.time.LocalDateTime;

public record DadosAgendamentoConsulta(

        @JsonProperty("id_medico")
        Long idMedico,

        @JsonProperty("id_paciente")
        @NotNull
        Long idPaciente,

        @NotNull
        @Future
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime data,

        Especialidade especialidade) {
}
