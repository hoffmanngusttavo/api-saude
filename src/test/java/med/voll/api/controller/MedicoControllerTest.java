package med.voll.api.controller;

import jakarta.persistence.EntityNotFoundException;
import med.voll.api.model.entity.endereco.DadosEndereco;
import med.voll.api.model.entity.endereco.Endereco;
import med.voll.api.model.entity.medico.*;
import med.voll.api.model.service.MedicoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class MedicoControllerTest {


    @Autowired
    private MockMvc mvc;

    @MockBean
    private MedicoService service;

    @Autowired
    private JacksonTester<DadosCadastroMedico> dadosCadastroMedicoJson;

    @Autowired
    private JacksonTester<DadosAtualizacaoMedico> dadosAtualizacaoMedicoJson;

    @Autowired
    private JacksonTester<DadosDetalhamentoMedico> dadosDetalhamentoMedicoJson;


    @Test
    @DisplayName("Deveria devolver codigo http 400 quando informacoes estao invalidas")
    @WithMockUser
    void cadastrar_cenario1() throws Exception {
        var response = mvc
                .perform(post("/medicos"))
                .andReturn().getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver codigo http 201 quando cadastrou médico com sucesso")
    @WithMockUser
    void cadastrar_cenario2() throws Exception {
        var dadosCadastro = new DadosCadastroMedico(
                "Medico",
                "gustavo@teste.com",
                "61999999999",
                "123456",
                Especialidade.CARDIOLOGIA,
                dadosEndereco());

        when(service.save(any())).thenReturn(new Medico(dadosCadastro));

        var response = mvc
                .perform(post("/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosCadastroMedicoJson.write(dadosCadastro).getJson()))
                .andReturn().getResponse();

        var dadosDetalhamento = new DadosDetalhamentoMedico(
                null,
                dadosCadastro.nome(),
                dadosCadastro.email(),
                dadosCadastro.crm(),
                dadosCadastro.telefone(),
                dadosCadastro.especialidade(),
                new Endereco(dadosCadastro.endereco())
        );
        var jsonEsperado = dadosDetalhamentoMedicoJson.write(dadosDetalhamento).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    @Test
    @DisplayName("Deveria devolver codigo http 400 quando não é enviado corpo na requisição")
    @WithMockUser
    void deve_atualizar_cenario1() throws Exception {
        var response = mvc
                .perform(put("/medicos"))
                .andReturn().getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver codigo http 400 quando não é enviado id do médico na requisição")
    @WithMockUser
    void deve_atualizar_cenario2() throws Exception {

        var dtoRequest = new DadosAtualizacaoMedico(
                null,
                "gustavo@teste.com",
                "61999999999",
                dadosEndereco());

        var response = mvc
                .perform(put("/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosAtualizacaoMedicoJson.write(dtoRequest).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }



    @Test
    @DisplayName("Deveria devolver codigo http 200 quando atualizou médico com sucesso")
    @WithMockUser
    void deve_atualizar_cenario3() throws Exception {
        var dtoRequest = new DadosAtualizacaoMedico(
                1L,
                "gustavo@teste.com",
                "61999999999",
                dadosEndereco());

        var medico = instanciarMedico();

        when(service.atualizarInformacoes(any())).thenReturn(medico);

        var response = mvc
                .perform(put("/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosAtualizacaoMedicoJson.write(dtoRequest).getJson()))
                .andReturn().getResponse();

        var dadosDetalhamento = new DadosDetalhamentoMedico(medico);

        var jsonEsperado = dadosDetalhamentoMedicoJson.write(dadosDetalhamento).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    @Test
    @DisplayName("Deve retornar erro 404 not found por nao encontrar id")
    @WithMockUser
    void deve_remover_cenario1() throws Exception {
        var idMedico = 1000L;
        doThrow(EntityNotFoundException.class).when(service).deleteById(idMedico);
        var response = mvc
                .perform(delete("/medicos/"+idMedico))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("Deve remover com sucesso e retornar 204")
    @WithMockUser
    void deve_remover_cenario2() throws Exception {
        var idMedico = 10L;
        doNothing().when(service).deleteById(idMedico);
        var response = mvc
                .perform(delete("/medicos/"+idMedico))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("Deve retornar erro 404 not found por nao encontrar id")
    @WithMockUser
    void deve_detalhar_cenario1() throws Exception {
        var idMedico = 1000L;
        doThrow(EntityNotFoundException.class).when(service).findById(idMedico);
        var response = mvc
                .perform(get("/medicos/"+idMedico))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("Deve retornar detalhe do médico e status 200")
    @WithMockUser
    void deve_detalhar_cenario2() throws Exception {
        var idMedico = 1l;
        var medico = instanciarMedico();
        when(service.findById(idMedico)).thenReturn(medico);
        var response = mvc
                .perform(get("/medicos/"+idMedico))
                .andReturn().getResponse();

        var dadosDetalhamento = new DadosDetalhamentoMedico(medico);
        var jsonEsperado = dadosDetalhamentoMedicoJson.write(dadosDetalhamento).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);

    }

    private Medico instanciarMedico() {
        var medico = new Medico();
        medico.setId(1l);
        medico.setNome("Gustavo");
        medico.setCrm("123");
        medico.setEmail("gustavo@teste.comm");
        medico.setTelefone("48999999");
        medico.setEspecialidade(Especialidade.CARDIOLOGIA);
        return medico;
    }

    private DadosEndereco dadosEndereco() {
        return new DadosEndereco(
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                null,
                null
        );
    }

}