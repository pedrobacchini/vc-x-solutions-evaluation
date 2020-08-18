package com.github.pedrobacchini.service;

import com.github.pedrobacchini.dto.CompanyDTO;
import com.github.pedrobacchini.entity.Company;
import com.github.pedrobacchini.enumerated.CompanyType;
import com.github.pedrobacchini.helper.TestHelper;
import com.github.pedrobacchini.mapper.CompanyMapper;
import com.github.pedrobacchini.repository.CompanyRepository;
import com.github.pedrobacchini.util.GenerateCpfCnpj;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Tag("unit")
class CompanyServiceImplTest extends TestHelper {

    CompanyService companyService;
    CompanyRepository companyRepository;
    CompanyMapper companyMapper;
    final ArgumentCaptor<Company> captor = ArgumentCaptor.forClass(Company.class);

    @BeforeEach
    void setup() {
        companyRepository = mock(CompanyRepository.class);
        companyMapper = mock(CompanyMapper.class);
        companyService = new CompanyServiceImpl(companyRepository, companyMapper);
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
        var companyDTO = new CompanyDTO(tradeName, name, documentIdentifier, type);
        Company expect = Company.builder()
                .tradeName(tradeName)
                .name(name)
                .documentIdentifier(documentIdentifier)
                .type(type)
                .build();
        when(companyMapper.toEntity(companyDTO)).thenReturn(expect);

        companyService.create(companyDTO);

        verify(companyMapper, times(1)).toEntity(companyDTO);
        verify(companyRepository, times(1)).save(captor.capture());
        Company actual = captor.getValue();
        assertThat(actual).isNotNull();
        assertThat(actual.getTradeName()).isEqualTo(tradeName);
        assertThat(actual.getName()).isEqualTo(name);
        assertThat(actual.getDocumentIdentifier()).isEqualTo(documentIdentifier);
        assertThat(actual.getType()).isEqualTo(type);
    }
}
