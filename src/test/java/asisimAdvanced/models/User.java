package asisimAdvanced.models;

import io.restassured.path.json.JsonPath;
import org.json.JSONObject;

public class User {
    private Long soldierId;
    private String username;
    private String password;
    private Long jobGroupId;

    public User(Long soldierId, String username, String password, Long jobGroupId) {
        this.soldierId = soldierId;
        this.username = username;
        this.password = password;
        this.jobGroupId = jobGroupId;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String asJsonCredentials() {
        JSONObject json = new JSONObject();
        json.put("username", this.username);
        json.put("password", this.password);

        return json.toString();
    }

    public Long soldierId() {
        return soldierId;
    }

    public void soldierId(Long soldierId) {
        this.soldierId = soldierId;
    }

    public String username() {
        return username;
    }

    public void username(String username) {
        this.username = username;
    }

    public String password() {
        return password;
    }

    public void password(String password) {
        this.password = password;
    }

    public Long jobGroupId() {
        return jobGroupId;
    }

    public void jobGroupId(Long jobGroupId) {
        this.jobGroupId = jobGroupId;
    }
}
