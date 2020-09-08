package asisimAdvanced.models;

import java.util.Date;

public class Soldier {
    private long id;
    private String name;
    private Date draftDate;
    private Date releaseDate;
    private long rankId;
    private long clinicId;

    public Soldier(long id, String name, Date draftDate, Date releaseDate, long rankId, long clinicId) {
        this.id = id;
        this.name = name;
        this.draftDate = draftDate;
        this.releaseDate = releaseDate;
        this.rankId = rankId;
        this.clinicId = clinicId;
    }

    public long id() {
        return id;
    }

    public void id(long id) {
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

    public long rankId() {
        return rankId;
    }

    public void rankId(long rankId) {
        this.rankId = rankId;
    }

    public long clinicId() {
        return clinicId;
    }

    public void clinicId(long clinicId) {
        this.clinicId = clinicId;
    }
}
