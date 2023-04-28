package med.voll.api.web.client;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import med.voll.api.web.client.exception.WebRequestClientException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import org.springframework.web.reactive.function.client.WebClient.UriSpec;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public abstract class WebRequestClient {

    protected WebClient client;

    protected WebRequestClient(){
        createInstanceWebClient();
    }


    protected abstract String getBaseUrl();

    private void createInstanceWebClient() {

        client = WebClient.builder()
                .baseUrl(getBaseUrl())
                .defaultCookie("cookieKey", "cookieValue")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(getClientConnector())
                .build();

    }

    protected  <K, T> Mono<K> post(String url, T request, Class<K> clazz) throws WebRequestClientException {
        UriSpec<RequestBodySpec> uriSpec = client.post();
        RequestBodySpec bodySpec = uriSpec.uri(url);
        RequestHeadersSpec<?> headersSpec = bodySpec.body(BodyInserters.fromValue(request));
        return headersSpec.exchangeToMono(response -> {
            if (response.statusCode().is2xxSuccessful()) {
                return response.bodyToMono(clazz);
            } else if (response.statusCode().is4xxClientError() || response.statusCode().is5xxServerError()) {
                throw new WebRequestClientException("Erro" + response.logPrefix());
            } else {
                return response.createException()
                        .flatMap(Mono::error);
            }
        });
    }

    protected <K, T> Mono<K> get(String url, Class<K> clazz) throws WebRequestClientException {
        RequestHeadersUriSpec<?> requestHeadersUriSpec = client.get();
        RequestHeadersSpec<?> uri = requestHeadersUriSpec.uri(url);
        return uri.accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(clazz);
   }



    protected ClientHttpConnector getClientConnector() {
        var httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .responseTimeout(Duration.ofMillis(5000))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)));

        return new ReactorClientHttpConnector(httpClient);
    }




}
