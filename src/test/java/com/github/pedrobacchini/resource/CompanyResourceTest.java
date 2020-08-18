package com.github.pedrobacchini.resource;

import com.github.pedrobacchini.dto.CompanyDTO;
import com.github.pedrobacchini.entity.Company;
import com.github.pedrobacchini.enumerated.CompanyType;
import com.github.pedrobacchini.helper.TestHelper;
import com.github.pedrobacchini.mapper.CompanyMapper;
import com.github.pedrobacchini.service.CompanyServiceImpl;
import com.github.pedrobacchini.util.GenerateCpfCnpj;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Tag("unit")
class CompanyResourceTest extends TestHelper {

    final ArgumentCaptor<Company> captor = ArgumentCaptor.forClass(Company.class);

    @Test
    void GIVEN_ValidCompany_MUST_ParseAndCallService() throws Exception {

        // given
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
        var companyMapper = mock(CompanyMapper.class);
        when(companyMapper.fromDTO(companyDTO)).thenReturn(expect);
        var companyService = mock(CompanyServiceImpl.class);
        when(companyService.create(expect)).thenReturn(expect);

        // when
        CompanyResource companyResource = new CompanyResource(companyService, companyMapper);
        companyResource.create(companyDTO);

        // then
        verify(companyMapper, times(1)).fromDTO(companyDTO);
        verify(companyService, times(1)).create(captor.capture());
        Company actual = captor.getValue();
        assertThat(actual).isNotNull();
        assertThat(actual.getTradeName()).isEqualTo(tradeName);
        assertThat(actual.getName()).isEqualTo(name);
        assertThat(actual.getDocumentIdentifier()).isEqualTo(documentIdentifier);
        assertThat(actual.getType()).isEqualTo(type);
    }

}
