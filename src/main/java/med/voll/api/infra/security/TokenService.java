package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import med.voll.api.model.entity.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    public static final String API_HOFFMANN_MED = "API HOFFMANN-MED";

    @Value("${api.security.token.secret}")
    private String secretKey;


    public String gerarToken(Usuario usuario){
        try {
            var algoritmo = Algorithm.HMAC256(secretKey);
            return JWT.create()
                    .withIssuer(API_HOFFMANN_MED) // QUEM ESTÁ GERANDO ESSE TOKEN
                    .withSubject(usuario.getLogin()) // para quem é esse token
                    .withClaim("id", usuario.getId()) // posso add outras infos no token, com chave e valor
                    .withExpiresAt(dataExpiracao()) // o token terá duração de 2 hrs
                    .sign(algoritmo);
        } catch (JWTCreationException exception){
            throw new RuntimeException("erro ao gerar token jwt", exception);
        }
    }

    public String getSubject(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(secretKey);
            return JWT.require(algoritmo)
                    .withIssuer(API_HOFFMANN_MED)
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now()
                .plusHours(2)
                .toInstant(ZoneOffset.of("-03:00"));
    }


}
