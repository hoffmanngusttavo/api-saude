package med.voll.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import med.voll.api.model.service.medicamentos.FarmaciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Farmácias")
@RestController
@RequestMapping("/farmacias")
public class FarmaciaController {

    @Autowired
    private FarmaciaService service;



}
