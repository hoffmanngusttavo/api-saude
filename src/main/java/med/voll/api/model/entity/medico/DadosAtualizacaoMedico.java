package med.voll.api.model.entity.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.api.model.entity.endereco.DadosEndereco;

public record DadosAtualizacaoMedico(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco) {
}
