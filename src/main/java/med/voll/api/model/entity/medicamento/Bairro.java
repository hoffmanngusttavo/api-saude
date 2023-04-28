package med.voll.api.model.entity.medicamento;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.model.entity.base.BaseEntity;
import med.voll.api.model.entity.medicamento.dto.DadosAtualizacaoBairro;
import med.voll.api.model.entity.medicamento.dto.DadosCadastroBairro;

@Entity
@Table(name = "bairros_farmacia")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Bairro implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;


    public Bairro(DadosCadastroBairro dados) {
        this.nome = dados.nome();
    }

    public Bairro(String nome) {
        this.nome = nome;
    }

    public Bairro(Long id) {
        this.id = id;
    }

    public void atualizarInformacoes(DadosAtualizacaoBairro dados) {
        this.nome = dados.nome();
    }
}
