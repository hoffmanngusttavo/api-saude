package med.voll.api.web.client;


import med.voll.api.model.entity.medicamento.dto.DadosIntegracaoMedicamentoPrefeitura;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Component
public class PrefeituraWebRequestClient extends WebRequestClient{

    @Value("${url.api.prefeitura}")
    private String urlApiPrefeitura;

    @Override
    protected String getBaseUrl() {
        return urlApiPrefeitura;
    }


    public Page<DadosIntegracaoMedicamentoPrefeitura> getRequest(LocalDate data, int limit, int page){
        return Page.empty();
    }



}
