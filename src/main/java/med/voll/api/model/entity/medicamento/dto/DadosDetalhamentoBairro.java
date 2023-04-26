package med.voll.api.model.entity.medicamento.dto;

import med.voll.api.model.entity.medicamento.Bairro;

public record DadosDetalhamentoBairro(Long id, String nome) {

    public DadosDetalhamentoBairro(Bairro medico){
        this(medico.getId(), medico.getNome());
    }

}