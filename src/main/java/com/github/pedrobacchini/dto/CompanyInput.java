package com.github.pedrobacchini.dto;

import com.github.pedrobacchini.enumerated.CompanyType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class CompanyInput {

    private final String tradeName;

    @NotNull
    private final String name;

    @NotNull
    private final String documentIdentifier;

    private final CompanyType type;
}

