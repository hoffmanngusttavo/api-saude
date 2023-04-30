package med.voll.api.model.entity.medicamento.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class ResultadoPaginacao<T> implements Serializable {

    protected List<T> content;

    protected int totalPages;

    protected int totalElements;

    protected int size;



}
