package med.voll.api.model.entity.consulta;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record DadosDetalhamentoConsulta(
        Long id,
        @JsonProperty("id_medico")
        Long idMedico,
        @JsonProperty("id_paciente")
        Long idPaciente,
        LocalDateTime data) {

    public DadosDetalhamentoConsulta(Consulta consulta){
        this(consulta.getId(), consulta.getMedico().getId(), consulta.getPaciente().getId(), consulta.getData());
    }

}