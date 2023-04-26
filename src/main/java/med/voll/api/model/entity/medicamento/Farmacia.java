package med.voll.api.model.entity.medicamento;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.model.entity.base.BaseEntity;
import med.voll.api.model.entity.medicamento.dto.DadosAtualizacaoFarmacia;
import med.voll.api.model.entity.medicamento.dto.DadosCadastroFarmacia;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalTime;

@Entity
@Table(name = "farmacias")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Farmacia implements BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String endereco;

    private String telefone;

    @Column(name = "horario_inicio_funcionamento")
    private LocalTime horarioInicioFuncionamento;

    @Column(name = "horario_termino_funcionamento")
    private LocalTime horarioTerminoFuncionamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bairro_id")
    private Bairro bairro;

    public Farmacia(DadosCadastroFarmacia dados) {
        this.bairro = new Bairro(dados.idBairro());
        this.nome = dados.nome();
        this.telefone = dados.telefone();
        this.endereco = dados.endereco();
        this.horarioInicioFuncionamento = dados.horarioInicioFuncionamento();
        this.horarioTerminoFuncionamento = dados.horarioTerminoFuncionamento();
    }

    public void atualizarInformacoes(DadosAtualizacaoFarmacia dados) {
        this.nome = StringUtils.isNotBlank(dados.nome()) ? dados.nome() : this.nome;
        this.telefone = StringUtils.isNotBlank(dados.telefone()) ? dados.telefone() : this.telefone;
        this.endereco = StringUtils.isNotBlank(dados.endereco()) ? dados.endereco() : this.endereco;
        this.horarioInicioFuncionamento = dados.horarioInicioFuncionamento() != null ? dados.horarioInicioFuncionamento() : this.horarioInicioFuncionamento;
        this.horarioTerminoFuncionamento = dados.horarioTerminoFuncionamento() != null ? dados.horarioTerminoFuncionamento() : this.horarioTerminoFuncionamento;
    }
}
