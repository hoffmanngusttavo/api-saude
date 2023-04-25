package med.voll.api.controller;

import jakarta.persistence.EntityNotFoundException;
import med.voll.api.model.entity.endereco.DadosEndereco;
import med.voll.api.model.entity.endereco.Endereco;
import med.voll.api.model.entity.paciente.DadosAtualizacaoPaciente;
import med.voll.api.model.entity.paciente.DadosCadastroPaciente;
import med.voll.api.model.entity.paciente.DadosDetalhamentoPaciente;
import med.voll.api.model.entity.paciente.Paciente;
import med.voll.api.model.service.PacienteService;
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
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class PacienteControllerTest {

    public static final String RESOURCE_PACIENTES = "/pacientes";
    @Autowired
    private MockMvc mvc;

    @MockBean
    private PacienteService service;

    @Autowired
    private JacksonTester<DadosCadastroPaciente> dadosCadastroPacienteJson;

    @Autowired
    private JacksonTester<DadosAtualizacaoPaciente> dadosAtualizacaoPacienteJson;

    @Autowired
    private JacksonTester<DadosDetalhamentoPaciente> dadosDetalhamentoPacienteJson;

    @Test
    @DisplayName("Deveria devolver codigo http 400 quando informacoes estao invalidas")
    void cadastrar_cenario1() throws Exception {
        var response = mvc
                .perform(post(RESOURCE_PACIENTES))
                .andReturn().getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver codigo http 201 quando cadastrou paciente com sucesso")
    void cadastrar_cenario2() throws Exception {
        var dadosCadastro = new DadosCadastroPaciente(
                "Paciente teste",
                "gustavo@teste.com",
                "61999999999",
                "032.101.180-51",
                dadosEndereco());

        when(service.save(any())).thenReturn(new Paciente(dadosCadastro));

        var response = mvc
                .perform(post(RESOURCE_PACIENTES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosCadastroPacienteJson.write(dadosCadastro).getJson()))
                .andReturn().getResponse();

        var dadosDetalhamento = new DadosDetalhamentoPaciente(
                null,
                dadosCadastro.nome(),
                dadosCadastro.email(),
                dadosCadastro.cpf(),
                dadosCadastro.telefone(),
                new Endereco(dadosCadastro.endereco())
        );
        var jsonEsperado = dadosDetalhamentoPacienteJson.write(dadosDetalhamento).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }


    @Test
    @DisplayName("Deveria devolver codigo http 400 quando não é enviado corpo na requisição")
    void deve_atualizar_cenario1() throws Exception {
        var response = mvc
                .perform(put(RESOURCE_PACIENTES))
                .andReturn().getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }


    @Test
    @DisplayName("Deveria devolver codigo http 400 quando não é enviado id do paciente na requisição")
    void deve_atualizar_cenario2() throws Exception {

        var dtoRequest = new DadosAtualizacaoPaciente(
                null,
                "gustavo@teste.com",
                "61999999999",
                dadosEndereco());

        var response = mvc
                .perform(put(RESOURCE_PACIENTES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosAtualizacaoPacienteJson.write(dtoRequest).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }


    @Test
    @DisplayName("Deveria devolver codigo http 200 quando atualizou paciente com sucesso")
    void deve_atualizar_cenario3() throws Exception {
        var dtoRequest = new DadosAtualizacaoPaciente(
                1L,
                "gustavo@teste.com",
                "61999999999",
                dadosEndereco());

        var paciente = instanciarPaciente();

        when(service.atualizarInformacoes(any())).thenReturn(paciente);

        var response = mvc
                .perform(put(RESOURCE_PACIENTES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosAtualizacaoPacienteJson.write(dtoRequest).getJson()))
                .andReturn().getResponse();

        var dadosDetalhamento = new DadosDetalhamentoPaciente(paciente);

        var jsonEsperado = dadosDetalhamentoPacienteJson.write(dadosDetalhamento).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }


    @Test
    @DisplayName("Deve retornar erro 404 not found por nao encontrar id")
    void deve_remover_cenario1() throws Exception {
        var idPaciente = 1000L;
        doThrow(EntityNotFoundException.class).when(service).deleteById(idPaciente);
        var response = mvc
                .perform(delete(RESOURCE_PACIENTES +"/" + idPaciente))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("Deve remover com sucesso e retornar 204")
    void deve_remover_cenario2() throws Exception {
        var idPaciente = 10L;
        doNothing().when(service).deleteById(idPaciente);
        var response = mvc
                .perform(delete(RESOURCE_PACIENTES+"/"+idPaciente))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }


    @Test
    @DisplayName("Deve retornar erro 404 not found por nao encontrar id")
    void deve_detalhar_cenario1() throws Exception {
        var idPaciente = 1000L;
        doThrow(EntityNotFoundException.class).when(service).findById(idPaciente);
        var response = mvc
                .perform(get(RESOURCE_PACIENTES+"/"+idPaciente))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("Deve retornar detalhe do paciente e status 200")
    void deve_detalhar_cenario2() throws Exception {
        var idPaciente = 1l;
        var paciente = instanciarPaciente();
        when(service.findById(idPaciente)).thenReturn(paciente);
        var response = mvc
                .perform(get(RESOURCE_PACIENTES+"/"+idPaciente))
                .andReturn().getResponse();

        var dadosDetalhamento = new DadosDetalhamentoPaciente(paciente);
        var jsonEsperado = dadosDetalhamentoPacienteJson.write(dadosDetalhamento).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }


    private Paciente instanciarPaciente() {
        var medico = new Paciente();
        medico.setId(1l);
        medico.setNome("Gustavo");
        medico.setCpf("123");
        medico.setEmail("gustavo@teste.comm");
        medico.setTelefone("48999999");
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