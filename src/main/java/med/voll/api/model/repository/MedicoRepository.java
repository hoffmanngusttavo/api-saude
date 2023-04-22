package med.voll.api.model.repository;

import med.voll.api.model.entity.medico.Especialidade;
import med.voll.api.model.entity.medico.Medico;
import med.voll.api.model.repository.generic.GenericCrudRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
public interface MedicoRepository extends GenericCrudRepository<Medico> {

    Page<Medico> findAllByAtivoTrue(Pageable paginacao);


    @Query("""
        select m from Medico m
        where m.ativo = true and m.especialidade = :especialidade
        order by rand()
        limit 1
    """)
    Medico escolherMedicoAleatorioLivreNaData(Especialidade especialidade);



}
