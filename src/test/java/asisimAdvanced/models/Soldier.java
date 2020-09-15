package asisimAdvanced.models;

import asisimAdvanced.managers.ClinicManager;
import asisimAdvanced.managers.RankManager;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Soldier {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("draftDate")
    private Date draftDate;
    @JsonProperty("releaseDate")
    private Date releaseDate;
    @JsonProperty("rankId")
    private Long rankId;
    @JsonProperty("clinicId")
    private Long clinicId;

    public Soldier() {

    }

    public Soldier(Long id, String name, Date draftDate, Date releaseDate, Long rankId, Long clinicId) {
        this.id = id;
        this.name = name;
        this.draftDate = draftDate;
        this.releaseDate = releaseDate;
        this.rankId = rankId;
        this.clinicId = clinicId;
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

    public Date draftDate() {
        return draftDate;
    }

    public void draftDate(Date draftDate) {
        this.draftDate = draftDate;
    }

    public Date releaseDate() {
        return releaseDate;
    }

    public void releaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Long rankId() {
        return rankId;
    }

    public void rankId(Long rankId) {
        this.rankId = rankId;
    }

    public String rankName() {
        return RankManager.getInstance().getById(rankId()).name();
    }

    public Long clinicId() {
        return clinicId;
    }

    public void clinicId(Long clinicId) {
        this.clinicId = clinicId;
    }

    public String clinicName() {
        return ClinicManager.getInstance().getById(clinicId()).name();
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return true;
        }

        if (obj instanceof Soldier) {
            Soldier soldier = (Soldier) obj;

            return this.id().equals(soldier.id());
        }

        return false;
    }
}
