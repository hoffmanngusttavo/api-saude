package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.model.entity.medico.DadosAtualizacaoMedico;
import med.voll.api.model.entity.medico.DadosListagemMedico;
import med.voll.api.model.entity.medico.DadosCadastroMedico;
import med.voll.api.model.entity.medico.Medico;
import med.voll.api.model.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoService service;

    @PostMapping
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados){
       service.save(new Medico(dados));
    }

    @PutMapping
    public void atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados){
        service.atualizarInformacoes(dados);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id){
        service.deleteById(id);
    }

    @GetMapping
    public Page<DadosListagemMedico> lista(@PageableDefault(size = 15, sort = {"nome"}) Pageable paginacao){
        return service.findAll(paginacao)
                .map(DadosListagemMedico::new);

    }


}
