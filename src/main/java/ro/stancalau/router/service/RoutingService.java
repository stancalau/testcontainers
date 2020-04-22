package ro.stancalau.router.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ro.stancalau.router.config.HopConfig;
import ro.stancalau.router.model.Hop;
import ro.stancalau.router.model.HopPath;

import java.util.Optional;

@Service
public class RoutingService {

    private final HopConfig config;
    private final RestTemplate restTemplate;
    private final Hop currentHop;

    public RoutingService(HopConfig config, RestTemplate restTemplate) {
        this.config = config;
        this.restTemplate = restTemplate;
        currentHop = new Hop(config.getName(), "hidden");
    }

    public HopPath route(String destination, String message) {
        if (config.getName().equals(destination)) {
            return new HopPath(currentHop);
        } else {
            Optional<HopPath> callPath = callHops(destination, message);
            if (callPath.isPresent()) {
                HopPath hopPath = callPath.get();
                hopPath.add(currentHop);
                return callPath.get();
            }
        }

        throw new IllegalArgumentException("Could not find path to destination " + destination);
    }

    private Optional<HopPath> callHops(String destination, String message) {
        Optional<Hop> target = config.getHopByName(destination);
        if (target.isPresent()) {
            return callOutbound(target.get(), destination, message);
        } else {
            for (Hop h : config.getOutbound()) {
                Optional<HopPath> hopPath = callOutbound(h, destination, message);
                if (hopPath.isPresent()) {
                    return hopPath;
                }
            }
        }
        return Optional.empty();
    }

    private Optional<HopPath> callOutbound(Hop hop, String destination, String message) {
        String url = UriComponentsBuilder
                .fromHttpUrl(hop.getPath() + "/api/route/{dest}/{message}")
                .buildAndExpand(destination, message)
                .toUriString();

        try {
            HopPath hopPath = restTemplate.getForObject(url, HopPath.class);
            return Optional.of(hopPath);
        } catch (RestClientException e) {
            return Optional.empty();
        }
    }
}
