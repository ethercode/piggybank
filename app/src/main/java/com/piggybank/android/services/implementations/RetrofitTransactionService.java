package com.piggybank.android.services.implementations;

import com.piggybank.android.models.service.TransactionModel;
import com.piggybank.android.services.Service;
import com.piggybank.android.services.Services;

import java.util.HashMap;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public class RetrofitTransactionService implements Service {
    private final SecureEndpoint secureEndpoint;

    public RetrofitTransactionService() {
        this.secureEndpoint = Services.getInstance().getSecureRestAdapter().create(SecureEndpoint.class);
    }

    public SecureEndpoint getSecureEndpoint() {
        return secureEndpoint;
    }

    public interface SecureEndpoint {
        @GET("/transaction/account/{account}")
        HashMap<String, TransactionModel> getAllTransactions(@Path("account") String account);

        @POST("/transaction/account/{account}")
        TransactionModel postTransaction(@Path("account") String account, @Body TransactionModel model);

    }

}
