package com.tsl.app.service.responsedto.tsl;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetTokenResponse  {

    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("token")
    @Expose
    private String token;

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
