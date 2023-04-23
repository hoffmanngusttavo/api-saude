package med.voll.api.model.service.consultas.validacoes.cancelamento;

import med.voll.api.exceptions.ValidacaoException;
import med.voll.api.model.entity.consulta.DadosCancelamentoConsulta;
import med.voll.api.model.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component("ValidadorHorarioAntecedenciaCancelamento")
public class ValidadorHorarioAntecedencia implements ValidadorCancelamentoConsulta{

    @Autowired
    private ConsultaRepository repository;


    @Override
    public void validar(DadosCancelamentoConsulta dados) {

        var consulta = repository.getReferenceById(dados.idConsulta());
        var agora = LocalDateTime.now();
        var diferencaEmHoras = Duration.between(agora, consulta.getData()).toHours();

        if (diferencaEmHoras < 24) {
            throw new ValidacaoException("Consulta somente pode ser cancelada com antecedência mínima de 24h!");
        }

    }
}
