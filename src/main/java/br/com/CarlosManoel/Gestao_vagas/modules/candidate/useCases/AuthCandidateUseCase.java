package br.com.CarlosManoel.Gestao_vagas.modules.candidate.useCases;

import br.com.CarlosManoel.Gestao_vagas.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.CarlosManoel.Gestao_vagas.modules.candidate.repositories.CandidateRepository;
import br.com.CarlosManoel.Gestao_vagas.modules.company.dto.AuthCandidateResponseDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Service
public class AuthCandidateUseCase {

    @Value("${spring.security.token.candidate}")
    private String secretKey;


    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateRequestDTO) throws AuthenticationException{
        var candidate = this.candidateRepository.findByUsername(authCandidateRequestDTO.username())
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("Username/Password incorrect");
                });
        var passwordMacthes = this.passwordEncoder.matches(authCandidateRequestDTO.password(), candidate.getPassword());

        if(!passwordMacthes) {
            throw new AuthenticationException();
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var expireIn = Instant.now().plus(Duration.ofMinutes(5));
        var token = JWT.create().withIssuer("GesVagas")
                .withSubject(candidate.getId().toString())
                .withExpiresAt(expireIn)
                .withClaim("roles", Arrays.asList("CANDIDATE"))
                .sign(algorithm);

        var authCandidateResponse = AuthCandidateResponseDTO.builder()
                .access_token(token)
                .expires_in(expireIn.toEpochMilli())
                .build();

        return authCandidateResponse;
    }
}
