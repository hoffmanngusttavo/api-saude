package med.voll.api.model.service.medicamentos;


import med.voll.api.model.entity.medicamento.Bairro;
import med.voll.api.model.entity.medicamento.dto.DadosAtualizacaoBairro;
import med.voll.api.model.repository.BairroRepository;
import med.voll.api.model.service.impl.GenericCrudServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class BairroServiceImpl extends GenericCrudServiceImpl<Bairro>
        implements BairroService {


    @Override
    @Transactional
    public Bairro atualizarInformacoes(DadosAtualizacaoBairro dados) {
        var bairro = this.findById(dados.id());
        bairro.atualizarInformacoes(dados);
        return bairro;
    }

    @Override
    public Optional<Bairro> findByNome(String nome) {
        return ((BairroRepository) repository).findByNome(nome);
    }


}
