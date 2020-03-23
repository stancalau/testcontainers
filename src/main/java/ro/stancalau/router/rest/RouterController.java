package ro.stancalau.router.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ro.stancalau.router.model.HopPath;
import ro.stancalau.router.service.RoutingService;

@RestController
public class RouterController {

    private final RoutingService service;

    public RouterController(RoutingService service) {
        this.service = service;
    }

    @GetMapping(value = "/api/route/{destination}/{message}")
    public ResponseEntity<HopPath> route(@PathVariable String destination, @PathVariable String message) {
        HopPath route = service.route(destination, message);
        return ResponseEntity.ok(route);
    }
}
