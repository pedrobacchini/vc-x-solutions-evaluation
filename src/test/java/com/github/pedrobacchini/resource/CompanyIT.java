package com.github.pedrobacchini.resource;

import com.github.pedrobacchini.dto.CompanyDTO;
import com.github.pedrobacchini.enumerated.CompanyType;
import com.github.pedrobacchini.helper.IntegrationHelper;
import com.github.pedrobacchini.repository.CompanyRepository;
import com.github.pedrobacchini.util.GenerateCpfCnpj;
import com.github.pedrobacchini.util.JsonUtil;
import com.github.pedrobacchini.util.MockUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CompanyIT extends IntegrationHelper {

    @Autowired
    private CompanyRepository repository;

    @Test
    void GIVEN_ValidPayload_MUST_ReturnCreated() throws Exception {

        // given
        var tradeName = faker.company().name();
        var name = tradeName + " SA";
        var documentIdentifier = GenerateCpfCnpj.cnpj(false);
        var type = CompanyType.random();
        var companyDTO = new CompanyDTO(tradeName, name, documentIdentifier, type);

        // when
        var createCompany = mockMvc.perform(post(CompanyResource.ENDPOINT_PATH)
                .contentType("application/json")
                .content(JsonUtil.toJson(companyDTO)))
                .andExpect(header().exists("Location"))
                .andExpect(status().isCreated()).andReturn();

        // then
        var id = MockUtils.getIdFromLocation(createCompany.getResponse());
        var companyOptional = repository.findById(id);
        assertThat(companyOptional).isNotEmpty();
        var company = companyOptional.get();
        assertThat(company.getTradeName()).isEqualTo(tradeName);
        assertThat(company.getName()).isEqualTo(name);
        assertThat(company.getDocumentIdentifier()).isEqualTo(documentIdentifier);
        assertThat(company.getType()).isEqualTo(type);
    }
}
