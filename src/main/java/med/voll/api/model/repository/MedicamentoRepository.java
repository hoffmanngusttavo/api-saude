package med.voll.api.model.repository;

import med.voll.api.model.entity.medicamento.Medicamento;
import med.voll.api.model.repository.generic.GenericCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicamentoRepository extends GenericCrudRepository<Medicamento> {

}
