package com.piggybank.android.models.service;

import com.google.gson.annotations.SerializedName;
import com.piggybank.android.models.base.BaseModel;
import com.piggybank.android.services.types.IsoDate;

public class TransactionModel extends BaseModel {
    @SerializedName("account_1_key")
    private String account1Key;

    @SerializedName("account_2_key")
    private String account2Key;

    @SerializedName("amount")
    private float amount;

    @SerializedName("create_time")
    private IsoDate createTime;

    public TransactionModel(String account2Key, float amount) {
        this.account2Key = account2Key;
        this.amount = amount;
    }

    public String getAccount1Key() {
        return account1Key;
    }

    public void setAccount1Key(String account1Key) {
        this.account1Key = account1Key;
    }

    public String getAccount2Key() {
        return account2Key;
    }

    public void setAccount2Key(String account2Key) {
        this.account2Key = account2Key;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public IsoDate getCreateTime() {
        return createTime;
    }

    public void setCreateTime(IsoDate createTime) {
        this.createTime = createTime;
    }
}
