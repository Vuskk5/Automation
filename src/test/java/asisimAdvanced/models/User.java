package asisimAdvanced.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class User {
    private Long soldierId;
    @Expose
    private String username;
    @Expose
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
        Gson gson = new GsonBuilder()
                            .excludeFieldsWithoutExposeAnnotation()
                            .create();

        return gson.toJson(this);
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
