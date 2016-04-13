package com.piggybank.android.models.service;

import com.google.gson.annotations.SerializedName;
import com.piggybank.android.models.base.BaseModel;
import com.piggybank.android.services.types.IsoDate;

public class AccountModel extends BaseModel {
    @SerializedName("key")
    private String key;

    @SerializedName("create_time")
    private IsoDate createTime;

    @SerializedName("modified_time")
    private IsoDate modifiedTime;

    @SerializedName("balance")
    private float balance;

    @SerializedName("friendly_name")
    private String friendlyName;

    public AccountModel(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public IsoDate getCreateTime() {
        return createTime;
    }

    public void setCreateTime(IsoDate createTime) {
        this.createTime = createTime;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public IsoDate getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(IsoDate modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
}
