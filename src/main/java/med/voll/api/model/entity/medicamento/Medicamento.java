package med.voll.api.model.entity.medicamento;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.model.entity.base.BaseEntity;
import med.voll.api.model.entity.medicamento.dto.DadosAtualizacaoMedicamento;
import med.voll.api.model.entity.medicamento.dto.DadosCadastroMedicamento;
import org.apache.commons.lang3.StringUtils;

@Entity
@Table(name = "medicamentos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medicamento implements BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String laboratorio;

    private Boolean disponivel;

    private double miligramas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farmacia_id")
    private Farmacia farmacia;


    public Medicamento(DadosCadastroMedicamento dados) {
        this.farmacia = new Farmacia(dados.idFarmacia());
        this.nome = dados.nome();
        this.laboratorio = dados.laboratorio();
        this.disponivel = dados.disponivel();
        this.miligramas = dados.miligramas();
    }

    public void atualizarInformacoes(DadosAtualizacaoMedicamento dados) {
        this.nome = StringUtils.isNotBlank(dados.nome()) ? dados.nome() : this.nome;
        this.laboratorio = StringUtils.isNotBlank(dados.laboratorio()) ? dados.laboratorio() : this.laboratorio;
        this.miligramas = dados.miligramas();
    }

    public void tornarIndisponivel(){
        this.disponivel = Boolean.FALSE;
    }

}
