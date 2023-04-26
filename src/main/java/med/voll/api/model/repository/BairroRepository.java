package med.voll.api.model.repository;

import med.voll.api.model.entity.medicamento.Bairro;
import med.voll.api.model.repository.generic.GenericCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BairroRepository extends GenericCrudRepository<Bairro> {

}
