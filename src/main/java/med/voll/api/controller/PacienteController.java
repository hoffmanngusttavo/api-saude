package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.model.entity.medico.DadosListagemMedico;
import med.voll.api.model.entity.paciente.DadosAtualizacaoPaciente;
import med.voll.api.model.entity.paciente.DadosCadastroPaciente;
import med.voll.api.model.entity.paciente.DadosListagemPaciente;
import med.voll.api.model.entity.paciente.Paciente;
import med.voll.api.model.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService service;

    @PostMapping
    public void cadastrar(@RequestBody @Valid DadosCadastroPaciente dados){
       service.save(new Paciente(dados));
    }


    @GetMapping
    public Page<DadosListagemPaciente> lista(@PageableDefault(size = 15, sort = {"nome"}) Pageable paginacao){
        return service.findAllByAtivoTrue(paginacao)
                .map(DadosListagemPaciente::new);
    }

    @PutMapping
    public void atualizar(@RequestBody @Valid DadosAtualizacaoPaciente dados) {
        service.atualizarInformacoes(dados);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id){
        service.deleteById(id);
    }

}
