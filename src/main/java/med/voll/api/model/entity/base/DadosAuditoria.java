package med.voll.api.model.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import java.time.LocalDateTime;

@Getter
@Setter
@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DadosAuditoria {

    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    private String createdBy;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime created;

    @LastModifiedBy
    @Column(name = "updated_by", nullable = false)
    private String updatedBy;

    @LastModifiedBy
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updated;


    public void prePersist(){
        created = LocalDateTime.now();
        updated = LocalDateTime.now();
    }

    public void preUpdate(){
        updated = LocalDateTime.now();
    }

}
