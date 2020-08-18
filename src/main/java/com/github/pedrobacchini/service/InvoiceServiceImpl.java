package com.github.pedrobacchini.service;

import com.github.pedrobacchini.dto.InvoiceDTO;
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
    public Invoice create(InvoiceDTO invoiceDTO) {
        Invoice invoice = invoiceMapper.toEntity(invoiceDTO, companyService);
        return invoiceRepository.save(invoice);
    }
}
