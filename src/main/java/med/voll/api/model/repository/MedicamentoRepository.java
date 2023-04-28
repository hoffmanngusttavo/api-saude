package med.voll.api.model.repository;

import med.voll.api.model.entity.medicamento.Medicamento;
import med.voll.api.model.repository.generic.GenericCrudRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicamentoRepository extends GenericCrudRepository<Medicamento> {


    Page<Medicamento> findAllByDisponivelTrue(Pageable paginacao);

    @Query("""
        select m from Medicamento m
        join m.farmacia f
        where 1=1 
        and f.bairro.id = ?1 
        and (UPPER(m.nome) like CONCAT('%',UPPER(?2),'%') or ?2 is null)
            """)
    Page<Medicamento> findByMedicamentosBairro(Long idBairro, String medicamento, Pageable paginacao);

    Optional<Medicamento> findByIdExterno(String uuid);
}
