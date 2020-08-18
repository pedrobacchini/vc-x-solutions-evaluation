package com.github.pedrobacchini.integration;

import com.github.pedrobacchini.helper.IntegrationHelper;
import com.github.pedrobacchini.repository.InvoiceRepository;
import com.github.pedrobacchini.util.JsonUtil;
import com.github.pedrobacchini.util.MockUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.github.pedrobacchini.resource.InvoiceResource.ENDPOINT_PATH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class InvoiceIT extends IntegrationHelper {

    @Autowired
    private InvoiceRepository repository;

    @Test
    void GIVEN_ValidPayload_MUST_ReturnCreated() throws Exception {

        // given
        var number = faker.number().randomNumber();
        var date = faker.date().future(1, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        var value = BigDecimal.valueOf(faker.number().randomDouble(2, 1L, 10000L));
        var takerId = 1L;
        var providerId = 2L;

        var expected = new HashMap<>();
        expected.put("number", number);
        expected.put("date", date.toString());
        expected.put("value", value);
        expected.put("takerId", takerId);
        expected.put("providerId", providerId);

        // when
        var createInvoice = mockMvc.perform(post(ENDPOINT_PATH)
                .contentType(APPLICATION_JSON)
                .content(JsonUtil.toJson(expected)))
                .andExpect(header().exists("Location"))
                .andExpect(status().isCreated()).andReturn();

        // then
        var id = MockUtils.getIdFromLocation(createInvoice.getResponse());
        var invoiceOptional = repository.findById(id);
        assertThat(invoiceOptional).isNotEmpty();
        var actual = invoiceOptional.get();
        assertThat(actual.getNumber()).isEqualTo(expected.get("number"));
        assertThat(actual.getDate().toString()).hasToString(expected.get("date").toString());
        assertThat(actual.getValue()).isEqualTo(expected.get("value"));
        assertThat(actual.getTaker().getId()).isEqualTo(expected.get("takerId"));
        assertThat(actual.getProvider().getId()).isEqualTo(expected.get("providerId"));
    }
}
