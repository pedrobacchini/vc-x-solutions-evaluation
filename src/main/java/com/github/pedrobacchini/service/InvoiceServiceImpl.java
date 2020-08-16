package com.github.pedrobacchini.service;

import com.github.pedrobacchini.entity.Invoice;
import com.github.pedrobacchini.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;

    @Override
    public Invoice create(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }
}
