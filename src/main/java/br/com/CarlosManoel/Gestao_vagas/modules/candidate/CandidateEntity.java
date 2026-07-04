package br.com.CarlosManoel.Gestao_vagas.modules.candidate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Data
public class CandidateEntity {

    private UUID id;
    private String name;
    @Pattern(regexp = "\\S+", message = "O username não deve conter espaços")
    private String username;

    @Email( message = "O email deve ser válido")
    private String email;

    @Length(min = 8, max = 50, message = "Senha deve Ser maior que 8 caracteres")
    private String password;
    private String description;
    private String curriculum;


}
