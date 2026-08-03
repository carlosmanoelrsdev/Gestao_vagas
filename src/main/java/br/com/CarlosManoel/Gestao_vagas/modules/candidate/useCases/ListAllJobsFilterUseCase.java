package br.com.CarlosManoel.Gestao_vagas.modules.candidate.useCases;

import br.com.CarlosManoel.Gestao_vagas.modules.company.entities.JobEntity;
import br.com.CarlosManoel.Gestao_vagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListAllJobsFilterUseCase {

    @Autowired
    private JobRepository jobRepository;

    public List<JobEntity> execute(String filter) {

        return this.jobRepository.findByDescriptionContainingIgnoreCase(filter);
    }
}
