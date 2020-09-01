package com.github.pedrobacchini.resource;

import com.github.pedrobacchini.dto.InvoiceInput;
import com.github.pedrobacchini.entity.Company;
import com.github.pedrobacchini.entity.Invoice;
import com.github.pedrobacchini.helper.TestHelper;
import com.github.pedrobacchini.service.InvoiceServiceImpl;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.net.URI;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

import static com.github.pedrobacchini.resource.InvoiceResource.ENDPOINT_PATH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Tag("unit")
class InvoiceResourceTest extends TestHelper {

    @Test
    void GIVEN_ValidCompany_MUST_CallService() throws Exception {

        // given
        var id = 1L;
        var number = faker.number().randomNumber();
        var date = faker.date().future(1, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        var value = BigDecimal.valueOf(faker.number().randomDouble(2, 1L, 10000L));
        var takerId = 1L;
        var providerId = 2L;
        var invoiceInput = new InvoiceInput(number, date, value, takerId, providerId);
        var expect = Invoice.builder()
                .id(id)
                .number(number)
                .date(date)
                .value(value)
                .taker(Company.builder().id(takerId).build())
                .provider(Company.builder().id(providerId).build())
                .build();

        var invoiceService = mock(InvoiceServiceImpl.class);
        when(invoiceService.create(invoiceInput)).thenReturn(expect);

        // when
        InvoiceResource invoiceResource = new InvoiceResource(invoiceService);
        ResponseEntity<Void> responseEntity = invoiceResource.create(invoiceInput);

        // then
        verify(invoiceService, times(1)).create(invoiceInput);
        assertThat(responseEntity.getHeaders().getLocation()).isEqualTo(new URI(String.format("%s/%s", ENDPOINT_PATH, id)));
    }
}
