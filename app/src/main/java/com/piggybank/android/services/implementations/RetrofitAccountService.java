package com.piggybank.android.services.implementations;

import com.piggybank.android.models.service.AccountModel;
import com.piggybank.android.services.Service;
import com.piggybank.android.services.Services;

import java.util.HashMap;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

public class RetrofitAccountService implements Service {
    private final SecureEndpoint secureEndpoint;

    public RetrofitAccountService() {
        this.secureEndpoint = Services.getInstance().getSecureRestAdapter().create(SecureEndpoint.class);
    }

    public SecureEndpoint getSecureEndpoint() {
        return secureEndpoint;
    }

    public interface SecureEndpoint {
        @GET("/account")
        HashMap<String, AccountModel> getAllAccounts();

        @GET("/account/{account}")
        AccountModel getAccount(@Path("account") String account);

        @POST("/account")
        AccountModel postAccount(@Body AccountModel accountModel);

        @PUT("/account/{account}")
        AccountModel putAccount(@Path("account") String account, @Body AccountModel model);

        @DELETE("/account/{account}")
        Response deleteAccount(@Path("account") String account);
    }
}
