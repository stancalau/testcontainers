package ro.stancalau.router.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ro.stancalau.router.model.Hop;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ConfigurationProperties(prefix = "hop")
@Configuration
public class HopConfig {

    private String name;
    private List<Hop> outbound;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Hop> getOutbound() {
        return Collections.unmodifiableList(outbound);
    }

    public void setOutbound(List<Hop> outbound) {
        this.outbound = outbound;
    }

    public Optional<Hop> getHopByName(String destination) {
        return outbound.stream()
                .filter(h -> h.getName() == destination)
                .findFirst();
    }
}
