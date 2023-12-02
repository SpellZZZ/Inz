package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RouteDto implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    private int id;
    private String name;
    private String data;
    private String dataEnd;

}