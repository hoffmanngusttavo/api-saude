package med.voll.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import med.voll.api.model.entity.medico.*;
import med.voll.api.model.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@Tag(name = "Médicos")
@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoService service;

    @Operation(description = "Cadastrar Médico")
    @PostMapping
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder) {
        var medicoCriado = service.save(new Medico(dados));
        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medicoCriado.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medicoCriado));
    }

    @Operation(description = "Atualizar Médico")
    @PutMapping
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        var medico = service.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @Operation(description = "Remover Médico")
    @DeleteMapping("/{id}")
    public ResponseEntity remover(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(description = "Buscar Médico pelo id")
    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var medico = service.findById(id);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @Operation(description = "Buscar todos os médicos ativos")
    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>> lista(@PageableDefault(size = 15, sort = {"nome"}) Pageable paginacao) {
        var page = service.findAllByAtivoTrue(paginacao)
                .map(DadosListagemMedico::new);
        return ResponseEntity.ok(page);
    }


}
