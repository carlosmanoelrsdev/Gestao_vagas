package br.com.CarlosManoel.Gestao_vagas.modules.candidate.useCases;

import br.com.CarlosManoel.Gestao_vagas.exceptions.JobNotFoundException;
import br.com.CarlosManoel.Gestao_vagas.exceptions.UserNotFoundException;
import br.com.CarlosManoel.Gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import br.com.CarlosManoel.Gestao_vagas.modules.candidate.repositories.ApplyJobRepository;
import br.com.CarlosManoel.Gestao_vagas.modules.candidate.repositories.CandidateRepository;
import br.com.CarlosManoel.Gestao_vagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApplyJobCandidateUseCase {

    private final ApplyJobRepository applyJobRepository;
    private final CandidateRepository candidateRepository;
    private final JobRepository jobRepository;

    public ApplyJobCandidateUseCase(
            ApplyJobRepository applyJobRepository,
            CandidateRepository candidateRepository,
            JobRepository jobRepository
    ) {
        this.applyJobRepository = applyJobRepository;
        this.candidateRepository = candidateRepository;
        this.jobRepository = jobRepository;
    }

    public ApplyJobEntity execute(UUID idCandidate, UUID idJob) {

        this.candidateRepository.findById(idCandidate)
                .orElseThrow(() -> new UserNotFoundException());

        this.jobRepository.findById(idJob)
                .orElseThrow(() -> new JobNotFoundException());

        var applyJob = ApplyJobEntity.builder()
                .candidateId(idCandidate)
                .jobId(idJob)
                .build();

        return this.applyJobRepository.save(applyJob);
    }
}