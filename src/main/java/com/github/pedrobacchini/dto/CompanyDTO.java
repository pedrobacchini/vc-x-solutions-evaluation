package com.github.pedrobacchini.dto;

import com.github.pedrobacchini.enumerated.CompanyType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CompanyDTO {

    private final String tradeName;
    private final String name;
    private final String documentIdentifier;
    private final CompanyType type;
}

