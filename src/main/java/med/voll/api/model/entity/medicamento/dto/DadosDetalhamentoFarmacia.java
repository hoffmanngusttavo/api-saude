package med.voll.api.model.entity.medicamento.dto;

import med.voll.api.model.entity.medicamento.Farmacia;

import java.time.LocalTime;

public record DadosDetalhamentoFarmacia(Long id,
                                        String nome,
                                        String telefone,
                                        String endereco,
                                        LocalTime horarioInicioFuncionamento,
                                        LocalTime horarioTerminoFuncionamento) {

    public DadosDetalhamentoFarmacia(Farmacia farmacia){
        this(farmacia.getId(), farmacia.getNome(), farmacia.getTelefone(),
                farmacia.getEndereco(), farmacia.getHorarioInicioFuncionamento(),
                farmacia.getHorarioTerminoFuncionamento());
    }

}