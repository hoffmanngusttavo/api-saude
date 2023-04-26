package med.voll.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import med.voll.api.model.entity.medicamento.Bairro;
import med.voll.api.model.entity.medicamento.dto.DadosAtualizacaoBairro;
import med.voll.api.model.entity.medicamento.dto.DadosCadastroBairro;
import med.voll.api.model.entity.medicamento.dto.DadosDetalhamentoBairro;
import med.voll.api.model.service.medicamentos.BairroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@Tag(name = "Bairros farmácias")
@RestController
@RequestMapping("/bairros-farmacia")
public class BairroController {

    @Autowired
    private BairroService service;


    @Operation(description = "Cadastrar bairros da farmácia")
    @PostMapping
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroBairro dados, UriComponentsBuilder uriBuilder) {
        var bairro = service.save(new Bairro(dados));
        var uri = uriBuilder.path("/bairros-farmacia/{id}").buildAndExpand(bairro.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoBairro(bairro));
    }

    @Operation(description = "Atualizar bairro farmácia")
    @PutMapping
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoBairro dados) {
        var bairro = service.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoBairro(bairro));
    }

    @Operation(description = "Remover Bairro")
    @DeleteMapping("/{id}")
    public ResponseEntity remover(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(description = "Buscar Bairro pelo id")
    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var bairro = service.findById(id);
        return ResponseEntity.ok(new DadosDetalhamentoBairro(bairro));
    }

    @Operation(description = "Buscar todos os bairros")
    @GetMapping
    public ResponseEntity<Page<DadosDetalhamentoBairro>> lista(@PageableDefault(size = 15, sort = {"nome"}) Pageable paginacao) {
        var page = service.findAll(paginacao).map(DadosDetalhamentoBairro::new);
        return ResponseEntity.ok(page);
    }


}
