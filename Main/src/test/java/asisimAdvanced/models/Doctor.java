package asisimAdvanced.models;

public class Doctor {
    private Long soldierId;
    private Long specId;
    private String professionalRank;

    public Doctor(Long soldierId, Long specId, String professionalRank) {
        this.soldierId = soldierId;
        this.specId = specId;
        this.professionalRank = professionalRank;
    }

    public Long soldierId() {
        return soldierId;
    }

    public void soldierId(Long soldierId) {
        this.soldierId = soldierId;
    }

    public Long specId() {
        return specId;
    }

    public void specId(Long specId) {
        this.specId = specId;
    }

    public String professionalRank() {
        return professionalRank;
    }

    public void professionalRank(String professionalRank) {
        this.professionalRank = professionalRank;
    }
}
