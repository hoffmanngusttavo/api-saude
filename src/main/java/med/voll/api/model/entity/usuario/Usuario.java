package med.voll.api.model.entity.usuario;


import jakarta.persistence.*;
import lombok.*;
import med.voll.api.model.entity.base.BaseEntity;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario  implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;

    private String senha;

}
