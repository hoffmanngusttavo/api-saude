package med.voll.api.model.entity.consulta;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.time.LocalDateTime;

public record DadosDetalhamentoConsulta(
        Long id,
        @JsonAlias("id_medico")
        Long idMedico,
        @JsonAlias("id_paciente")
        Long idPaciente,
        LocalDateTime data) {

    public DadosDetalhamentoConsulta(Consulta consulta){
        this(consulta.getId(), consulta.getMedico().getId(), consulta.getPaciente().getId(), consulta.getData());
    }

}