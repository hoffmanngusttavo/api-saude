package med.voll.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import med.voll.api.infra.docs.SpringDocConfigurations;
import med.voll.api.model.entity.consulta.DadosAgendamentoConsulta;
import med.voll.api.model.entity.consulta.DadosCancelamentoConsulta;
import med.voll.api.model.service.consultas.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Agendamento de Consultas")
@RestController
@RequestMapping("/consultas")
@SecurityRequirement(name = SpringDocConfigurations.BEARER_KEY)
public class ConsultaController {

    @Autowired
    private ConsultaService service;


    @Operation(description = "Cadastrar Agendamento de consulta")
    @PostMapping
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) {
        var deatlhesAgenda = service.agendar(dados);
        return ResponseEntity.ok(deatlhesAgenda);
    }

    @Operation(description = "Cancelar Agendamento de consulta")
    @DeleteMapping
    public ResponseEntity cancelar(@RequestBody @Valid DadosCancelamentoConsulta dados) {
        service.cancelar(dados);
        return ResponseEntity.noContent().build();
    }

}
