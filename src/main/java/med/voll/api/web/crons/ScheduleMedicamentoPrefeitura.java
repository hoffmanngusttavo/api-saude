package med.voll.api.web.crons;

import med.voll.api.model.service.medicamentos.MedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class ScheduleMedicamentoPrefeitura {


    @Autowired
    private MedicamentoService medicamentoService;



    private static final String TIME_ZONE = "America/Sao_Paulo";

    //@Scheduled(cron = "*/30 * * * * *", zone = TIME_ZONE)
    @Scheduled(cron = "0 0 8 ? * *", zone = TIME_ZONE)
    public void importarMedicamentos() {
        medicamentoService.importarMedicamentosPrefeitura();
    }

}
