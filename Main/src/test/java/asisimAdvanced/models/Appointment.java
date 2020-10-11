package asisimAdvanced.models;

import asisimAdvanced.managers.SeverityManager;
import asisimAdvanced.managers.SoldierManager;
import asisimAdvanced.support.util.ToStringHelper;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Appointment {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("soldierId")
    private Long soldierId;
    @JsonProperty("doctorId")
    private Long doctorId;
    @JsonProperty("severityId")
    private Long severityId;
    @JsonProperty("beginTime")
    private LocalDateTime beginTime;
    @JsonProperty("reason")
    private String reason;
    @JsonProperty("measurementId")
    private Long measurementId;
    @JsonProperty("soldierDesc")
    private String soldierDesc;
    @JsonProperty("doctorFinds")
    private String doctorFinds;

    public Appointment() {

    }

    public Appointment(Long id, Long soldierId, Long doctorId, Long severityId, LocalDateTime beginTime, String reason, Long measurementId, String soldierDesc, String doctorFinds) {
        this.id = id;
        this.soldierId = soldierId;
        this.doctorId = doctorId;
        this.severityId = severityId;
        this.beginTime = beginTime;
        this.reason = reason;
        this.measurementId = measurementId;
        this.soldierDesc = soldierDesc;
        this.doctorFinds = doctorFinds;
    }

    public Long id() {
        return id;
    }

    public void id(Long id) {
        this.id = id;
    }

    public Long soldierId() {
        return soldierId;
    }

    public void soldierId(Long soldierId) {
        this.soldierId = soldierId;
    }

    public String soldierName() {
        return SoldierManager.getInstance().getById(soldierId).name();
    }

    public Long doctorId() {
        return doctorId;
    }

    public void doctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String doctorName() {
        return SoldierManager.getInstance().getById(doctorId).name();
    }

    public Long severityId() {
        return severityId;
    }

    public void severityId(Long severityId) {
        this.severityId = severityId;
    }

    public String severityName() {
        return SeverityManager.getInstance().getById(severityId).name();
    }

    public LocalDateTime beginTime() {
        return beginTime;
    }

    public void beginTime(LocalDateTime beginTime) {
        this.beginTime = beginTime;
    }

    public String reason() {
        return reason;
    }

    public void reason(String reason) {
        this.reason = reason;
    }

    public Long measurementId() {
        return measurementId;
    }

    public void measurementId(Long measurementId) {
        this.measurementId = measurementId;
    }

    public String soldierDesc() {
        return soldierDesc;
    }

    public void soldierDesc(String soldierDesc) {
        this.soldierDesc = soldierDesc;
    }

    public String doctorFinds() {
        return doctorFinds;
    }

    public void doctorFinds(String doctorFinds) {
        this.doctorFinds = doctorFinds;
    }
}
