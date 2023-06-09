package med.voll.api.model.entity.medico;

import jakarta.persistence.*;
import lombok.*;
import med.voll.api.model.entity.base.BaseEntity;
import med.voll.api.model.entity.endereco.Endereco;
import org.springframework.util.StringUtils;

@Entity
@Table(name = "medicos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico implements BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String crm;
    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;
    @Embedded
    private Endereco endereco;
    private Boolean ativo;


    public Medico(DadosCadastroMedico dados) {
        this.ativo = Boolean.TRUE;
        this.nome = dados.nome();
        this.crm = dados.crm();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.especialidade = dados.especialidade();
        this.endereco = new Endereco(dados.endereco());
    }


    public void atualizarInformacoes(DadosAtualizacaoMedico dados) {
        this.nome = dados.nome() != null ? dados.nome() : nome;
        this.telefone = dados.telefone() != null ? dados.telefone() : telefone;
        if(dados.endereco() != null){
            this.endereco.atualizarInformacoes(dados.endereco());
        }
    }

    public void excluir() {
        this.ativo = Boolean.FALSE;
    }
}
