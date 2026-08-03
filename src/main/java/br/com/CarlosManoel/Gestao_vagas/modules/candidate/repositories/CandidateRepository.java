package br.com.CarlosManoel.Gestao_vagas.modules.candidate.repositories;

import br.com.CarlosManoel.Gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.CarlosManoel.Gestao_vagas.modules.company.entities.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CandidateRepository extends JpaRepository<CandidateEntity, UUID> {
    Optional<CandidateEntity> findByUsernameOrEmail(String username, String email);
    Optional<CandidateEntity> findByUsername(String username);
}
