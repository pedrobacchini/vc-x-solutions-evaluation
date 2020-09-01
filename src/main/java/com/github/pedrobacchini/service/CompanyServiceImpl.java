package com.github.pedrobacchini.service;

import com.github.pedrobacchini.dto.CompanyInput;
import com.github.pedrobacchini.entity.Company;
import com.github.pedrobacchini.mapper.CompanyMapper;
import com.github.pedrobacchini.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Override
    public Company create(CompanyInput companyInput) {
        Company company = companyMapper.toEntity(companyInput);
        return companyRepository.save(company);
    }

    @Override
    public Company findById(Long id) {
        return companyRepository.findById(id).get();
    }
}
