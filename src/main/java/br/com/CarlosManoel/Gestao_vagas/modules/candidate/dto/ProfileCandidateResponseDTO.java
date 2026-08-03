package br.com.CarlosManoel.Gestao_vagas.modules.candidate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCandidateResponseDTO {

    @Schema(example = "desenvolvedor java", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;
    @Schema(example = "ROBERTAMARTINS", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;
    @Schema(example = "Roberta@contato.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;
    private UUID id;
    @Schema(example = "Roberta Martins", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
}
