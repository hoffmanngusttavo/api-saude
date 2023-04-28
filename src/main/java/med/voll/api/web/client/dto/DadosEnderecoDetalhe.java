package med.voll.api.web.client.dto;

public record DadosEnderecoDetalhe(

        String cep,

        String logradouro,

        String complemento,

        String bairro,

        String localidade,

        String uf,

        String ibge,
        String ddd) {
}
