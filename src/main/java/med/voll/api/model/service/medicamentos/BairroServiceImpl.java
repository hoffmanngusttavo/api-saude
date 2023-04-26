package med.voll.api.model.service.medicamentos;


import med.voll.api.model.entity.medicamento.Bairro;
import med.voll.api.model.service.impl.GenericCrudServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class BairroServiceImpl extends GenericCrudServiceImpl<Bairro>
        implements BairroService {


    
}
