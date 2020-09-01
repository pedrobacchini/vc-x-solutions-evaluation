package com.github.pedrobacchini.service;

import com.github.pedrobacchini.dto.CompanyInput;
import com.github.pedrobacchini.entity.Company;

public interface CompanyService {

    Company create(CompanyInput companyInput);

    Company findById(Long id);
}
