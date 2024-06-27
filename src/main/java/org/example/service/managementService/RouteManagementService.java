package org.example.service.managementService;

import org.example.dto.RouteAddDto;
import org.example.dto.RouteDataDto;
import org.example.dto.RouteDto;
import org.example.model.Route;
import org.example.model.User;

import java.util.List;

public interface RouteManagementService {
    public void createRoute(RouteAddDto routeAddDto, User user);
    public List<RouteDto> getRoutes(User user);
    public RouteDataDto getRouteDate(Route route, User user);

}
