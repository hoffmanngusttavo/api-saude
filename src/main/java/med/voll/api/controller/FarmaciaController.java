package med.voll.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import med.voll.api.model.entity.medicamento.Farmacia;
import med.voll.api.model.entity.medicamento.dto.*;
import med.voll.api.model.service.medicamentos.FarmaciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@Tag(name = "Farmácias")
@RestController
@RequestMapping("/farmacias")
public class FarmaciaController {

    @Autowired
    private FarmaciaService service;


    @Operation(description = "Cadastrar farmácia")
    @PostMapping
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroFarmacia dados, UriComponentsBuilder uriBuilder) {
        var farmacia = service.save(new Farmacia(dados));
        var uri = uriBuilder.path("/farmacias/{id}").buildAndExpand(farmacia.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoFarmacia(farmacia));
    }

    @Operation(description = "Atualizar dados da farmácia")
    @PutMapping
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoFarmacia dados) {
        var farmacia = service.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoFarmacia(farmacia));
    }

    @Operation(description = "Remover Farmácia")
    @DeleteMapping("/{id}")
    public ResponseEntity remover(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(description = "Buscar Farmácia pelo id")
    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var farmacia = service.findById(id);
        return ResponseEntity.ok(new DadosDetalhamentoFarmacia(farmacia));
    }


    @Operation(description = "Buscar todos as farmácias")
    @GetMapping
    public ResponseEntity<Page<DadosDetalhamentoFarmacia>> lista(@PageableDefault(size = 15, sort = {"nome"}) Pageable paginacao) {
        var page = service.findAll(paginacao).map(DadosDetalhamentoFarmacia::new);
        return ResponseEntity.ok(page);
    }

}
