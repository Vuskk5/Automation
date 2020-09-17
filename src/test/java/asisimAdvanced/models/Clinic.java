package asisimAdvanced.models;

import asisimAdvanced.managers.ClinicManager;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Clinic {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("address")
    private String address;

    public Clinic() {

    }

    public Clinic(Long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Clinic(Long id) {
        this(ClinicManager.getInstance().getById(id));
    }

    public Clinic(Clinic clone) {
        this.id = clone.id;
        this.name = clone.name;
        this.address = clone.address;
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

    public String address() {
        return address;
    }

    public void address(String address) {
        this.address = address;
    }
}
