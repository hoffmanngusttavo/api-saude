package med.voll.api.web.exception;

public class WebRequestClientException extends RuntimeException {
    public WebRequestClientException(String erro) {
        super(erro);
    }
}
