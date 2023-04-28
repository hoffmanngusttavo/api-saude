package med.voll.api.web.client;


import med.voll.api.web.client.dto.DadosEnderecoDetalhe;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CepEnderecoWebRequestClient extends WebRequestClient{

    @Override
    protected String getBaseUrl() {
        return "https://viacep.com.br/ws";
    }


    public DadosEnderecoDetalhe buscarDadosEnderecoByCep(String cep){
        String cepFormatado = cep.replaceAll("[^-?0-9]+", "");
        Mono<DadosEnderecoDetalhe> stringMono = get("/"+cepFormatado+"/json/", DadosEnderecoDetalhe.class);
        return stringMono.block();
    }



}
