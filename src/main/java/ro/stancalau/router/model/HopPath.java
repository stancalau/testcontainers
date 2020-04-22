package ro.stancalau.router.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HopPath hopPath = (HopPath) o;
        return hops.equals(hopPath.hops);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hops);
    }

    @Override
    public String toString() {
        return "HopPath{" +
                "hops=" + hops +
                '}';
    }
}
