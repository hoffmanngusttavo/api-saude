package med.voll.api.model.entity.medicamento.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import med.voll.api.model.entity.medicamento.Farmacia;

import java.time.LocalTime;

public record DadosDetalhamentoFarmacia(Long id,
                                        String nome,
                                        String telefone,
                                        String endereco,
                                        @JsonProperty("horario_inicio_funcionamento")
                                        @JsonFormat(pattern = "HH:mm")
                                        LocalTime horarioInicioFuncionamento,
                                        @JsonProperty("horario_termino_funcionamento")
                                        @JsonFormat(pattern = "HH:mm")
                                        LocalTime horarioTerminoFuncionamento,

                                        @JsonProperty("id_bairro")
                                        Long idBairro) {

    public DadosDetalhamentoFarmacia(Farmacia farmacia){
        this(farmacia.getId(), farmacia.getNome(), farmacia.getTelefone(),
                farmacia.getEndereco(), farmacia.getHorarioInicioFuncionamento(),
                farmacia.getHorarioTerminoFuncionamento(), farmacia.getBairro().getId());
    }

}