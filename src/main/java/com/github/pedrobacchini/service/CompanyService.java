package com.github.pedrobacchini.service;

import com.github.pedrobacchini.entity.Company;

public interface CompanyService {

    Company create(Company company);

    Company findById(Long id);
}
