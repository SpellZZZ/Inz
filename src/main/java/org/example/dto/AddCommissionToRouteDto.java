package org.example.dto;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


@Getter
@Setter
public class AddCommissionToRoute implements Serializable {

    private static final long serialVersionUID = 1L;

    private String route_id;
    private List<Integer> packages;

    public AddCommissionToRoute() {
    }

    public AddCommissionToRoute(String route_id, List<Integer> packages) {
        this.route_id = route_id;
        this.packages = packages;
    }

}
