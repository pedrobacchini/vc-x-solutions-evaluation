package com.github.pedrobacchini.service;

import com.github.pedrobacchini.dto.InvoiceInput;
import com.github.pedrobacchini.entity.Invoice;

public interface InvoiceService {

    Invoice create(InvoiceInput invoiceInput);
}
