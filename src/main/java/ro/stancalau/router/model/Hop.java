package ro.stancalau.router.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Hop {

    private String name;
    private String path;

    public Hop() {
    }

    public Hop(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hop hop = (Hop) o;
        return Objects.equals(name, hop.name) &&
                Objects.equals(path, hop.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, path);
    }

    @Override
    public String toString() {
        return "Hop{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
