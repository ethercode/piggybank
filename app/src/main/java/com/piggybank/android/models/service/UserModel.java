package com.piggybank.android.models.service;

import com.google.gson.annotations.SerializedName;
import com.piggybank.android.models.base.BaseModel;
import com.piggybank.android.services.types.IsoDate;

public class UserModel extends BaseModel {
    @SerializedName("name")
    private String name;

    @SerializedName("key")
    private String key;

    @SerializedName("secret")
    private String secret;

    @SerializedName("create_time")
    private IsoDate createTime;

    @SerializedName("modified_time")
    private IsoDate modifiedTime;

    public UserModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public IsoDate getCreateTime() {
        return createTime;
    }

    public void setCreateTime(IsoDate createTime) {
        this.createTime = createTime;
    }

    public IsoDate getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(IsoDate modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
}
