package asisimAdvanced.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Rank {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;

    public Rank() {

    }

    public Rank(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long id() {
        return id;
    }

    public void id(Long id) {
        this.id = id;
    }

    public String name() {
        return name;
    }

    public void name(String name) {
        this.name = name;
    }
}
