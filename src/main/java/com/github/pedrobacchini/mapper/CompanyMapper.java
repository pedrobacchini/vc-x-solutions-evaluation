package com.github.pedrobacchini.mapper;

import com.github.pedrobacchini.dto.CompanyInput;
import com.github.pedrobacchini.entity.Company;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    Company toEntity(CompanyInput companyInput);
}
