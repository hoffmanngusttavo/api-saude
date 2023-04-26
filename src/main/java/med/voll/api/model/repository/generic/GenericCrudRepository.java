package med.voll.api.model.repository.generic;

import med.voll.api.model.entity.base.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericCrudRepository<T extends BaseEntity> extends JpaRepository<T, Long> {



}
