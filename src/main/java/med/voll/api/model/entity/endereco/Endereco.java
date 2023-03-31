package med.voll.api.model.entity.endereco;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    private String logradouro;
    private String bairro;
    private String cep;
    private String numero;
    private String complemento;
    private String cidade;
    private String uf;


    public Endereco(DadosEndereco dados) {
        this.logradouro = dados.logradouro();
        this.bairro = dados.bairro();
        this.cep = dados.cep();
        this.uf = dados.uf();
        this.cidade = dados.cidade();
        this.complemento = dados.complemento();
        this.numero = dados.numero();
    }

    public void atualizarInformacoes(DadosEndereco dados) {
        this.logradouro = dados.logradouro() != null ? dados.logradouro() : logradouro;
        this.bairro = dados.bairro() != null ? dados.bairro() : bairro;
        this.cep = dados.cep() != null ? dados.cep() : cep;
        this.uf = dados.uf() != null ? dados.uf() : uf;
        this.cidade = dados.cidade() != null ? dados.cidade() : cidade;
        this.complemento = dados.complemento() != null ? dados.complemento() : complemento;
        this.numero = dados.numero() != null ? dados.numero() : numero;
    }
}
