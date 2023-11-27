package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.model.Address;
import org.example.model.Commission;
import org.example.model.Route;

import java.io.Serializable;
import java.util.List;


@Getter
@Setter
public class RouteDetailsDto implements Serializable {
    private static final long serialVersionUID = 5926468583005150707L;

    Address addressStart;
    Route route;
    List<Commission>commissions;



}
