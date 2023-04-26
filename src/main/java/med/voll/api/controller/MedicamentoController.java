package med.voll.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import med.voll.api.model.entity.medicamento.Medicamento;
import med.voll.api.model.entity.medicamento.dto.DadosAtualizacaoMedicamento;
import med.voll.api.model.entity.medicamento.dto.DadosCadastroMedicamento;
import med.voll.api.model.entity.medicamento.dto.DadosDetalhamentoMedicamento;
import med.voll.api.model.service.medicamentos.MedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@Tag(name = "Medicamentos")
@RestController
@RequestMapping("/medicamentos")
public class MedicamentoController {

    @Autowired
    private MedicamentoService service;


    @Operation(description = "Cadastrar medicamento")
    @PostMapping
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedicamento dados, UriComponentsBuilder uriBuilder) {
        var medicamento = service.save(new Medicamento(dados));
        var uri = uriBuilder.path("/medicamentos/{id}").buildAndExpand(medicamento.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedicamento(medicamento));
    }

    @Operation(description = "Atualizar dados do medicamento")
    @PutMapping
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedicamento dados) {
        var medicamento = service.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoMedicamento(medicamento));
    }

    @Operation(description = "Remover Medicamento")
    @DeleteMapping("/{id}")
    public ResponseEntity remover(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(description = "Buscar Medicamento pelo id")
    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var medicamento = service.findById(id);
        return ResponseEntity.ok(new DadosDetalhamentoMedicamento(medicamento));
    }


    @Operation(description = "Buscar todos os medicamentos disponíveis")
    @GetMapping
    public ResponseEntity<Page<DadosDetalhamentoMedicamento>> lista(@PageableDefault(size = 15, sort = {"nome"}) Pageable paginacao) {
        var page = service.findAllAtivo(paginacao).map(DadosDetalhamentoMedicamento::new);
        return ResponseEntity.ok(page);
    }



}
