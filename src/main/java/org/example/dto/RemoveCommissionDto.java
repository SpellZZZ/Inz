package org.example.dto;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RemoveCommissionDto implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private  int route_id;
    private  int commission_id;
}
