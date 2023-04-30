package med.voll.api.web.client;


import med.voll.api.model.entity.medicamento.dto.RetornoPaginacaoMedicamentoPrefeitura;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class PrefeituraWebRequestClient extends WebRequestClient {

    @Value("${url.api.prefeitura}")
    private String urlApiPrefeitura;

    @Override
    protected String getBaseUrl() {
        return urlApiPrefeitura;
    }


    public RetornoPaginacaoMedicamentoPrefeitura getRequest(LocalDate data, int size, int page) {
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        var dataFormatada = data.format(formatters);
        var urlFinal = urlApiPrefeitura + "?data=" + dataFormatada + "&page=" + page + "&size=" + size;
        Mono<RetornoPaginacaoMedicamentoPrefeitura> resultadoMono = get(urlFinal, RetornoPaginacaoMedicamentoPrefeitura.class);
        return resultadoMono.block();
    }


}
