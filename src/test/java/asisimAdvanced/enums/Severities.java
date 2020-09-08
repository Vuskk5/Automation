package asisimAdvanced.enums;

public enum Severities {
    CasualAppointment("Casual Appointment"),
    Exemption("Exemption"),
    Referral("Referral"),
    Urgent("Urgent"),
    Other("Other");

    private String severity;

    Severities(String severity) {
        this.severity = severity;
    }

    public String getValue() {
        return severity;
    }
}
