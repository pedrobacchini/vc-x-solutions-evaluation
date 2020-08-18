package com.github.pedrobacchini.service;

import com.github.pedrobacchini.dto.InvoiceDTO;
import com.github.pedrobacchini.entity.Invoice;

public interface InvoiceService {

    Invoice create(InvoiceDTO invoiceDTO);
}
