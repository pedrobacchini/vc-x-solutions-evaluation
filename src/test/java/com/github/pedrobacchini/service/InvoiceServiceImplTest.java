package com.github.pedrobacchini.service;

import com.github.pedrobacchini.dto.InvoiceInput;
import com.github.pedrobacchini.entity.Company;
import com.github.pedrobacchini.entity.Invoice;
import com.github.pedrobacchini.helper.TestHelper;
import com.github.pedrobacchini.mapper.InvoiceMapper;
import com.github.pedrobacchini.repository.InvoiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Tag("unit")
class InvoiceServiceImplTest extends TestHelper {

    InvoiceService invoiceService;
    InvoiceRepository invoiceRepository;
    InvoiceMapper invoiceMapper;
    CompanyService companyService;
    final ArgumentCaptor<Invoice> captor = ArgumentCaptor.forClass(Invoice.class);

    @BeforeEach
    void setup() {
        invoiceRepository = mock(InvoiceRepository.class);
        invoiceMapper = mock(InvoiceMapper.class);
        companyService = mock(CompanyService.class);
        invoiceService = new InvoiceServiceImpl(companyService, invoiceMapper, invoiceRepository);
    }

    @Test
    void MUST_ImplementInterface() {
        assertThat(invoiceService).isInstanceOf(InvoiceService.class);
    }

    @Test
    void GIVEN_ValidInvoice_MUST_CreateInvoiceInDatabase() {
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
        when(invoiceMapper.toEntity(invoiceInput, companyService)).thenReturn(expect);

        invoiceService.create(invoiceInput);

        verify(invoiceMapper, times(1)).toEntity(invoiceInput, companyService);
        verify(invoiceRepository, times(1)).save(captor.capture());
        Invoice actual = captor.getValue();
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(id);
        assertThat(actual.getNumber()).isEqualTo(number);
        assertThat(actual.getDate()).isEqualTo(date);
        assertThat(actual.getValue()).isEqualTo(value);
        assertThat(actual.getTaker()).isNotNull();
        assertThat(actual.getTaker().getId()).isEqualTo(takerId);
        assertThat(actual.getProvider()).isNotNull();
        assertThat(actual.getProvider().getId()).isEqualTo(providerId);
    }
}
