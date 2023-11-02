package org.example.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class CompanyFormDto implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;


    private String company_name;

    private String company_nip;

    public CompanyFormDto() {
    }

    public CompanyFormDto(String company_name, String company_nip) {
        this.company_name = company_name;
        this.company_nip = company_nip;
    }




}