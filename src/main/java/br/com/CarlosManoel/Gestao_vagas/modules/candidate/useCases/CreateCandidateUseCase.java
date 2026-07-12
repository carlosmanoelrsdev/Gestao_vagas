package br.com.CarlosManoel.Gestao_vagas.modules.candidate.useCases;

import br.com.CarlosManoel.Gestao_vagas.exceptions.UserFoundException;
import br.com.CarlosManoel.Gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.CarlosManoel.Gestao_vagas.modules.candidate.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    public CandidateEntity execute(CandidateEntity candidateEntity){
        this.candidateRepository.findByUsernameOrEmail
                        (candidateEntity.getUsername(), candidateEntity.getEmail())
                .ifPresent((user) -> {
                    throw new UserFoundException();
                });


        return this.candidateRepository.save(candidateEntity);

    }
}
