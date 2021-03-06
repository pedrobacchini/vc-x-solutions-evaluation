package com.github.pedrobacchini.resource;

import com.github.pedrobacchini.dto.CompanyInput;
import com.github.pedrobacchini.entity.Company;
import com.github.pedrobacchini.service.CompanyService;
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
public class CompanyResource {

    public static final String ENDPOINT_PATH = "/companies";

    private final CompanyService companyService;

    @PostMapping(path = ENDPOINT_PATH)
    public ResponseEntity<Void> create(@Valid @RequestBody CompanyInput companyInput) throws URISyntaxException {

        Company company = companyService.create(companyInput);

        return ResponseEntity
                .created(new URI(String.format("%s/%s", ENDPOINT_PATH, company.getId())))
                .build();
    }

}
