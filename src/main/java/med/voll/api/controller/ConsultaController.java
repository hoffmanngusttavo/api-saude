package med.voll.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import med.voll.api.model.entity.consulta.DadosAgendamentoConsulta;
import med.voll.api.model.entity.consulta.DadosCancelamentoConsulta;
import med.voll.api.model.entity.consulta.DadosDetalhamentoConsulta;
import med.voll.api.model.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Agendamento de Consultas")
@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaService service;


    @Operation(description = "Cadastrar Agendamento de consulta")
    @PostMapping
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) {

        service.agendar(dados);

        return ResponseEntity.ok(new DadosDetalhamentoConsulta(null, null, null, null));
    }

    @Operation(description = "Cancelar Agendamento de consulta")
    @DeleteMapping
    public ResponseEntity cancelar(@RequestBody @Valid DadosCancelamentoConsulta dados) {
        service.cancelar(dados);
        return ResponseEntity.noContent().build();
    }

}