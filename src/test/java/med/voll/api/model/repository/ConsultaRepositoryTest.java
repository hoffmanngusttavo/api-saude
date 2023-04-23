package med.voll.api.model.repository;

import med.voll.api.model.entity.consulta.Consulta;
import med.voll.api.model.entity.endereco.DadosEndereco;
import med.voll.api.model.entity.medico.DadosCadastroMedico;
import med.voll.api.model.entity.medico.Especialidade;
import med.voll.api.model.entity.medico.Medico;
import med.voll.api.model.entity.paciente.DadosCadastroPaciente;
import med.voll.api.model.entity.paciente.Paciente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;


@DataJpaTest
//configurar para usar o mesmo banco de dados e nao usar banco em memoria, tipo h2
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class ConsultaRepositoryTest {

    @Autowired
    private ConsultaRepository repository;

    @Autowired
    private TestEntityManager em;



    @Test
    @DisplayName("Deve existir uma consulta para o paciente no mesmo dia")
    void devePossuirConsultaPacienteCenario1() {
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        var primeiroHorario = proximaSegundaAs10.withHour(7);
        var ultimoHorario = proximaSegundaAs10.withHour(18);

        var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);
        var paciente = cadastrarPaciente("Paciente", "paciente@email.com", "00000000000");
        cadastrarConsulta(medico, paciente, proximaSegundaAs10);

        var possuiPaciente = repository.existsByPacienteIdAndDataBetween(paciente.getId(), primeiroHorario, ultimoHorario);
        Assertions.assertTrue(possuiPaciente);
    }

    @Test
    @DisplayName("Não deve existir uma consulta para outro paciente no mesmo dia")
    void naoDevePossuirConsultaPacienteCenario2() {
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        var primeiroHorario = proximaSegundaAs10.withHour(7);
        var ultimoHorario = proximaSegundaAs10.withHour(18);

        var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);
        var paciente = cadastrarPaciente("Paciente", "paciente@email.com", "00000000000");
        cadastrarConsulta(medico, paciente, proximaSegundaAs10);

        var paciente2 = cadastrarPaciente("Paciente 2", "paciente2@email.com", "2353453");

        var possuiPaciente = repository.existsByPacienteIdAndDataBetween(paciente2.getId(), primeiroHorario, ultimoHorario);
        Assertions.assertFalse(possuiPaciente);
    }

    @Test
    @DisplayName("Não deve existir uma consulta para o mesmo paciente em outro dia")
    void naoDevePossuirConsultaPacienteCenario3() {
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        var primeiroHorario = LocalDateTime.now().withHour(7);
        var ultimoHorario = LocalDateTime.now().withHour(18);

        var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);
        var paciente = cadastrarPaciente("Paciente", "paciente@email.com", "00000000000");
        cadastrarConsulta(medico, paciente, proximaSegundaAs10);

        var possuiPaciente = repository.existsByPacienteIdAndDataBetween(paciente.getId(), primeiroHorario, ultimoHorario);
        Assertions.assertFalse(possuiPaciente);
    }


    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
        em.persist(new Consulta(null, medico, paciente, data, null));
    }

    private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
        var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
        em.persist(medico);
        return medico;
    }

    private Paciente cadastrarPaciente(String nome, String email, String cpf) {
        var paciente = new Paciente(dadosPaciente(nome, email, cpf));
        em.persist(paciente);
        return paciente;
    }

    private DadosCadastroMedico dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
        return new DadosCadastroMedico(
                nome,
                email,
                "61999999999",
                crm,
                especialidade,
                dadosEndereco()
        );
    }

    private DadosCadastroPaciente dadosPaciente(String nome, String email, String cpf) {
        return new DadosCadastroPaciente(
                nome,
                email,
                "61999999999",
                cpf,
                dadosEndereco()
        );
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