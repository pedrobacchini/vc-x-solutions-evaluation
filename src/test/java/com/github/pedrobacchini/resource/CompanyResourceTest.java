package com.github.pedrobacchini.resource;

import com.github.pedrobacchini.dto.CompanyDTO;
import com.github.pedrobacchini.entity.Company;
import com.github.pedrobacchini.enumerated.CompanyType;
import com.github.pedrobacchini.helper.TestHelper;
import com.github.pedrobacchini.service.CompanyServiceImpl;
import com.github.pedrobacchini.util.GenerateCpfCnpj;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static com.github.pedrobacchini.resource.CompanyResource.ENDPOINT_PATH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Tag("unit")
class CompanyResourceTest extends TestHelper {

    @Test
    void GIVEN_ValidCompany_MUST_CallService() throws Exception {

        // given
        var id = 1L;
        var tradeName = faker.company().name();
        var name = tradeName + " SA";
        var documentIdentifier = GenerateCpfCnpj.cnpj(false);
        var type = CompanyType.random();
        var companyDTO = new CompanyDTO(tradeName, name, documentIdentifier, type);
        Company expect = Company.builder()
                .id(id)
                .tradeName(tradeName)
                .name(name)
                .documentIdentifier(documentIdentifier)
                .type(type)
                .build();

        var companyService = mock(CompanyServiceImpl.class);
        when(companyService.create(companyDTO)).thenReturn(expect);

        // when
        CompanyResource companyResource = new CompanyResource(companyService);
        ResponseEntity<Void> responseEntity = companyResource.create(companyDTO);

        // then
        verify(companyService, times(1)).create(companyDTO);
        assertThat(responseEntity.getHeaders().getLocation()).isEqualTo(new URI(String.format("%s/%s", ENDPOINT_PATH, id)));
    }
}
