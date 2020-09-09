package asisimAdvanced.models;

import asisimAdvanced.managers.ClinicManager;
import asisimAdvanced.managers.RankManager;

import java.util.Date;

public class Soldier {
    private Long id;
    private String name;
    private Date draftDate;
    private Date releaseDate;
    private Long rankId;
    private Long clinicId;

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
        return RankManager.byId(rankId()).name();
    }

    public Long clinicId() {
        return clinicId;
    }

    public void clinicId(Long clinicId) {
        this.clinicId = clinicId;
    }

    public String clinicName() {
        return ClinicManager.byId(clinicId()).name();
    }
}
