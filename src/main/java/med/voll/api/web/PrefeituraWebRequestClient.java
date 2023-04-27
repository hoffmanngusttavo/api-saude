package med.voll.api.web;


import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class PrefeituraWebRequestClient extends WebRequestClient{

    @Override
    protected String getBaseUrl() {
        return "https://viacep.com.br/ws";
    }


    public void getRequest(){
        //Mono<String> resposta =  post("/01001000/json/", new String("body"), String.class);
        Mono<String> stringMono = get("/01001000/json/", String.class);
        String block = stringMono.block();

    }



}
