package com.emazon.msstock.domain.model;

import com.emazon.msstock.domain.exception.EmptyFieldException;
import com.emazon.msstock.domain.exception.LengthFieldException;
import com.emazon.msstock.domain.util.DomainConstants;

import static java.util.Objects.requireNonNull;

public class Brand {
    private final Long id;
    private final String name;
    private final  String description;

    public Brand(Long id, String name, String description) {
        if(name == null || name.isEmpty()) {
            throw new EmptyFieldException(DomainConstants.Field.NAME.toString());
        }

        if(description == null || description.isEmpty()) {
            throw new EmptyFieldException(DomainConstants.Field.DESCRIPTION.toString());
        }

        if(name.length() > DomainConstants.MAXIMUM_LENGTH_NAME_BRAND) {
            throw new LengthFieldException(DomainConstants.Field.NAME.toString());
        }

        if(description.length() > DomainConstants.MAXIMUM_LENGTH_DESCRIPTION_BRAND) {
            throw new LengthFieldException(DomainConstants.Field.DESCRIPTION.toString());
        }

        this.id = id;
        this.name = requireNonNull(name, DomainConstants.FIELD_NAME_NULL_MESSAGE);
        this.description = requireNonNull(description, DomainConstants.FIELD_DESCRIPTIOM_NULL_MESSAGE);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() { return description; }
}
