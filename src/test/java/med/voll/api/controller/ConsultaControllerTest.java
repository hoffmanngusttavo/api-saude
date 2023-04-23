package med.voll.api.controller;

import med.voll.api.model.entity.consulta.DadosAgendamentoConsulta;
import med.voll.api.model.entity.consulta.DadosDetalhamentoConsulta;
import med.voll.api.model.entity.medico.Especialidade;
import med.voll.api.model.service.consultas.ConsultaService;
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

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ConsultaService service;

    @Autowired
    private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaJson;

    @Autowired
    private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsultaJson;

    @Test
    @DisplayName("Deveria devolver status 400 quando informações forem inválidas")
    //@WithMockUser - //quando add autenticação e precisar ignorar
    void deveRetornarStatus400AgendarCenario1() throws Exception {
        var response = mvc.perform(post("/consultas"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria retornar status 200 quando informações estão válidas")
    //@WithMockUser - //quando add autenticação e precisar ignorar
    void deveRetornarStatus200AgendarCenario1() throws Exception {

        var data = LocalDateTime.now().plusHours(1);
        var especialidade  = Especialidade.CARDIOLOGIA;

        var dadosAgendamento = new DadosAgendamentoConsulta(5l, 2l, data, especialidade);
        var dadosDetalhamento = new DadosDetalhamentoConsulta(null, 5l, 2l, data);

        when(service.agendar(any())).thenReturn(dadosDetalhamento);

        var response = mvc.
                perform(
                        post("/consultas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(dadosAgendamentoConsultaJson.write(dadosAgendamento).getJson())
                )
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonEsperado = dadosDetalhamentoConsultaJson
                .write(dadosDetalhamento)
                .getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }



}