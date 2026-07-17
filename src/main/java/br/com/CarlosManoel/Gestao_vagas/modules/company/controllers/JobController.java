package br.com.CarlosManoel.Gestao_vagas.modules.company.controllers;


import br.com.CarlosManoel.Gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.CarlosManoel.Gestao_vagas.modules.company.entities.JobEntity;
import br.com.CarlosManoel.Gestao_vagas.modules.company.useCases.CreateJobUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/company/job")
public class JobController {

    @Autowired
    private CreateJobUseCase createJobUseCase;

    @PostMapping("/")
    @PreAuthorize("hasRole('COMPANY')")
    public JobEntity create(@Valid @RequestBody CreateJobDTO createJobDTO, HttpServletRequest request) {

        var companyId = request.getAttribute("company_id");

        var jobEntity = JobEntity.builder().benefits(createJobDTO.getBenefits())
                .description(createJobDTO.getDescription())
                .companyId(UUID.fromString(companyId.toString()))
                .level(createJobDTO.getLevel())
                .build();

        return this.createJobUseCase.execute(jobEntity);
    }
}