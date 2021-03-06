package com.github.pedrobacchini.resource;

import com.github.pedrobacchini.dto.InvoiceInput;
import com.github.pedrobacchini.entity.Invoice;
import com.github.pedrobacchini.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
public class InvoiceResource {

    public static final String ENDPOINT_PATH = "/invoices";

    private final InvoiceService invoiceService;

    @PostMapping(path = ENDPOINT_PATH)
    public ResponseEntity<Void> create(@Valid @RequestBody InvoiceInput invoiceInput) throws URISyntaxException {

        Invoice invoice = invoiceService.create(invoiceInput);

        return ResponseEntity
                .created(new URI(String.format("%s/%s", ENDPOINT_PATH, invoice.getId())))
                .build();
    }

}
