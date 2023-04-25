package med.voll.api.infra.exceptions;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StandardError implements Serializable {

    private Instant timestamp;
    private Integer status;
    private List<String> errors;
    private String message;
    private String path;
}
