package com.github.pedrobacchini.service;

import com.github.pedrobacchini.entity.Company;
import com.github.pedrobacchini.enumerated.CompanyType;
import com.github.pedrobacchini.helper.TestHelper;
import com.github.pedrobacchini.repository.CompanyRepository;
import com.github.pedrobacchini.util.GenerateCpfCnpj;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CompanyServiceImplTest extends TestHelper {

    CompanyService companyService;
    CompanyRepository companyRepository;
    ArgumentCaptor<Company> captor = ArgumentCaptor.forClass(Company.class);

    @BeforeEach
    void setup() {
        companyRepository = mock(CompanyRepository.class);
        companyService = new CompanyServiceImpl(companyRepository);
    }

    @Test
    void MUST_ImplementInterface() {
        assertThat(companyService).isInstanceOf(CompanyService.class);
    }

    @Test
    void GIVEN_ValidCompany_MUST_CreateCompanyInDatabase() {
        var tradeName = faker.company().name();
        var name = tradeName + " SA";
        var documentIdentifier = GenerateCpfCnpj.cnpj(false);
        var type = CompanyType.random();

        companyService.create(new Company(tradeName, name, documentIdentifier, type));

        verify(companyRepository, times(1)).save(captor.capture());
        Company company = captor.getValue();
        assertThat(company).isNotNull();
        assertThat(company.getTradeName()).isEqualTo(tradeName);
        assertThat(company.getName()).isEqualTo(name);
        assertThat(company.getDocumentIdentifier()).isEqualTo(documentIdentifier);
        assertThat(company.getType()).isEqualTo(type);
    }
}
