package org.example.dto;


import lombok.Getter;
import lombok.Setter;
import org.example.model.Commission;

import java.io.Serializable;

@Getter
@Setter
public class CommissionSwapDto implements Serializable {
    private static final long serialVersionUID = 5926468583005150707L;

    private int first;
    private int second;


}
