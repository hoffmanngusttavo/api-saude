package med.voll.api.model.repository;

import med.voll.api.model.entity.medicamento.Medicamento;
import med.voll.api.model.repository.generic.GenericCrudRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicamentoRepository extends GenericCrudRepository<Medicamento> {


    Page<Medicamento> findAllByDisponivelTrue(Pageable paginacao);

}
