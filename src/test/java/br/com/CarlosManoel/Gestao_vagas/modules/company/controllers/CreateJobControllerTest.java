package br.com.CarlosManoel.Gestao_vagas.modules.company.controllers;

import br.com.CarlosManoel.Gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.CarlosManoel.Gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.CarlosManoel.Gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.CarlosManoel.Gestao_vagas.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static br.com.CarlosManoel.Gestao_vagas.utils.TestUtils.objectToJson;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreateJobControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private CompanyRepository companyRepository;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    public void should_be_able_to_create_a_new_job() throws Exception {
        var company = new CompanyEntity();
        company.setUsername("company-job-test");
        company.setEmail("company-job-test@email.com");
        company.setPassword("senha123");
        company.setName("Company Test");
        company.setDescription("Company for job creation test");
        company.setWebsite("https://company-test.com");
        company = companyRepository.save(company);

        var createJobDTO = CreateJobDTO.builder()
                .benefits("Benefits_test")
                .description("Description_test")
                .level("Level_test")
                .build();

        var result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/company/job/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(createJobDTO))
                        .header(
                                "Authorization",
                                "Bearer " + TestUtils.generateToken(company.getId())
                        )
        ).andExpect(status().isOk());

        System.out.println(result);
    }
}