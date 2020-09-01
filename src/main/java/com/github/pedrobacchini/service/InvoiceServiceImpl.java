package com.github.pedrobacchini.service;

import com.github.pedrobacchini.dto.InvoiceInput;
import com.github.pedrobacchini.entity.Invoice;
import com.github.pedrobacchini.mapper.InvoiceMapper;
import com.github.pedrobacchini.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final CompanyService companyService;
    private final InvoiceMapper invoiceMapper;
    private final InvoiceRepository invoiceRepository;

    @Override
    public Invoice create(InvoiceInput invoiceInput) {
        Invoice invoice = invoiceMapper.toEntity(invoiceInput, companyService);
        return invoiceRepository.save(invoice);
    }
}
