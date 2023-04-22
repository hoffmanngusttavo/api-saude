package med.voll.api.model.entity.paciente;

import jakarta.validation.constraints.NotNull;
import med.voll.api.model.entity.endereco.DadosEndereco;

public record DadosAtualizacaoPaciente(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco) {
}
