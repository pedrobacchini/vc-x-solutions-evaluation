package com.github.pedrobacchini.service;

import com.github.pedrobacchini.dto.CompanyDTO;
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
    public Company create(CompanyDTO companyDTO) {
        Company company = companyMapper.toEntity(companyDTO);
        return companyRepository.save(company);
    }

    @Override
    public Company findById(Long id) {
        return companyRepository.findById(id).get();
    }
}
