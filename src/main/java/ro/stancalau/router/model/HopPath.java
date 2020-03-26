package ro.stancalau.router.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HopPath {

    private List<Hop> hops = new ArrayList<>();

    public HopPath() {
    }

    public HopPath(Hop hop) {
        add(hop);
    }

    public List<Hop> getHops() {
        return Collections.unmodifiableList(hops);
    }

    public void add(Hop hop) {
        hops.add(hop);
    }
}
