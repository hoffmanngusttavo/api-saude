package med.voll.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import med.voll.api.model.entity.paciente.*;
import med.voll.api.model.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@Tag(name = "Pacientes")
@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService service;

    @Operation(description = "Cadastrar paciente")
    @PostMapping
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroPaciente dados, UriComponentsBuilder uriBuilder) {
        var paciente = service.save(new Paciente(dados));
        var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(paciente));
    }


    @Operation(description = "Listar todos pacientes ativos")
    @GetMapping
    public ResponseEntity<Page<DadosListagemPaciente>> lista(@PageableDefault(size = 15, sort = {"nome"}) Pageable paginacao) {
        var page = service.findAllByAtivoTrue(paginacao)
                .map(DadosListagemPaciente::new);
        return ResponseEntity.ok(page);
    }

    @Operation(description = "Atualizar paciente")
    @PutMapping
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoPaciente dados) {
        var paciente = service.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }


    @Operation(description = "Buscar paciente pelo id")
    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var paciente = service.findById(id);
        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }

    @Operation(description = "Remover paciente")
    @DeleteMapping("/{id}")
    public ResponseEntity remover(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
