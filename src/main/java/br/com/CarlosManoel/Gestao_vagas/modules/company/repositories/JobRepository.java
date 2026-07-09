package br.com.CarlosManoel.Gestao_vagas.modules.company.repositories;

import br.com.CarlosManoel.Gestao_vagas.modules.company.entities.JobEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JobRepository extends JpaRepository<JobEntity, UUID> {

}
