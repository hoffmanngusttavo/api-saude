package med.voll.api.model.repository;

import med.voll.api.model.entity.usuario.Usuario;
import med.voll.api.model.repository.generic.GenericCrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends GenericCrudRepository<Usuario> {

    UserDetails findByLogin(String login);
}
