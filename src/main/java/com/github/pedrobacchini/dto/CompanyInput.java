package com.github.pedrobacchini.dto;

import com.github.pedrobacchini.enumerated.CompanyType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CompanyInput {

    String tradeName;

    @NotNull
    String name;

    @NotNull
    String documentIdentifier;

    CompanyType type;
}

