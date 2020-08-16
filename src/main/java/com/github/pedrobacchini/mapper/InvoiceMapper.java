package com.github.pedrobacchini.mapper;

import com.github.pedrobacchini.dto.InvoiceDTO;
import com.github.pedrobacchini.entity.Company;
import com.github.pedrobacchini.entity.Invoice;
import com.github.pedrobacchini.service.CompanyService;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    @Mapping(source = "takerId", target = "taker", qualifiedByName = "findCompanyById")
    @Mapping(source = "providerId", target = "provider", qualifiedByName = "findCompanyById")
    Invoice fromDTO(InvoiceDTO invoiceDTO, @Context CompanyService companyService);

    @Named("findCompanyById")
    default Company findCompanyById(Long companyId, @Context CompanyService companyService) {
        return companyService.findById(companyId);
    }
}
