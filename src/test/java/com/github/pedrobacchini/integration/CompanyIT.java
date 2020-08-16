package com.github.pedrobacchini.integration;

import com.github.pedrobacchini.enumerated.CompanyType;
import com.github.pedrobacchini.helper.IntegrationHelper;
import com.github.pedrobacchini.repository.CompanyRepository;
import com.github.pedrobacchini.util.GenerateCpfCnpj;
import com.github.pedrobacchini.util.JsonUtil;
import com.github.pedrobacchini.util.MockUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static com.github.pedrobacchini.resource.CompanyResource.ENDPOINT_PATH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CompanyIT extends IntegrationHelper {

    @Autowired
    private CompanyRepository repository;

    @Test
    void GIVEN_ValidPayload_MUST_ReturnCreated() throws Exception {

        // given
        var expected = validCompany();

        // when
        var createCompany = mockMvc.perform(post(ENDPOINT_PATH)
                .contentType(APPLICATION_JSON)
                .content(JsonUtil.toJson(expected)))
                .andExpect(header().exists("Location"))
                .andExpect(status().isCreated()).andReturn();

        // then
        var id = MockUtils.getIdFromLocation(createCompany.getResponse());
        var companyOptional = repository.findById(id);
        assertThat(companyOptional).isNotEmpty();
        var actual = companyOptional.get();
        assertThat(actual.getTradeName()).isEqualTo(expected.get("tradeName"));
        assertThat(actual.getName()).isEqualTo(expected.get("name"));
        assertThat(actual.getDocumentIdentifier()).isEqualTo(expected.get("documentIdentifier"));
        assertThat(actual.getType()).isEqualTo(expected.get("type"));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidData")
    void GIVEN_InvalidData_MUST_ReturnBadRequest(String tradeName,
                                                 String name,
                                                 String documentIdentifier,
                                                 CompanyType type,
                                                 String[] errorsFields,
                                                 String[] errorsDetails) throws Exception {
        // given
        Map<String, Object> payload = new HashMap<>();
        payload.put("tradeName", tradeName);
        payload.put("name", name);
        payload.put("documentIdentifier", documentIdentifier);
        payload.put("type", type);

        // when
        mockMvc.perform(post(ENDPOINT_PATH)
                .contentType("application/json")
                .content(JsonUtil.toJson(payload)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Invalid field")))
                .andExpect(jsonPath("$.errors[*].field", containsInAnyOrder(errorsFields)))
                .andExpect(jsonPath("$.errors[*].detail", containsInAnyOrder(errorsDetails)));
    }

    private static Stream<Arguments> provideInvalidData() {

        var validCompany = validCompany();

        return Stream.of(
                arguments(validCompany.get("tradeName"), null, validCompany.get("documentIdentifier"), validCompany.get("type"), new String[]{"name"}, new String[]{"must not be null"}),
                arguments(validCompany.get("tradeName"), validCompany.get("name"), null, validCompany.get("type"), new String[]{"documentIdentifier"}, new String[]{"must not be null"})
        );
    }

    @Test
    void GIVEN_MalformedJson_MUST_ReturnBadRequest() throws Exception {

        // given
        var payload = validCompany();
        payload.put("type", "other");

        // when
        mockMvc.perform(post(ENDPOINT_PATH)
                .contentType("application/json")
                .content(JsonUtil.toJson(payload)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Malformed JSON")));
    }

    private static Map<String, Object> validCompany() {
        var tradeName = faker.company().name();
        var name = tradeName + " SA";
        var documentIdentifier = GenerateCpfCnpj.cnpj(false);
        var type = CompanyType.random();

        var payload = new HashMap<String, Object>();
        payload.put("tradeName", tradeName);
        payload.put("name", name);
        payload.put("documentIdentifier", documentIdentifier);
        payload.put("type", type);

        return payload;
    }
}
