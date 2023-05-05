package med.voll.api.model.service.impl;


import med.voll.api.model.entity.usuario.Usuario;
import med.voll.api.model.service.UsuarioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class UsuarioServiceImpl extends GenericCrudServiceImpl<Usuario>
        implements UsuarioService {



}
