package com.github.pedrobacchini.service;

import com.github.pedrobacchini.dto.CompanyDTO;
import com.github.pedrobacchini.entity.Company;

public interface CompanyService {

    Company create(CompanyDTO companyDTO);

    Company findById(Long id);
}
