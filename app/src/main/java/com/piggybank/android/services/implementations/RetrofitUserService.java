package com.piggybank.android.services.implementations;

import com.piggybank.android.models.service.UserModel;
import com.piggybank.android.services.Service;
import com.piggybank.android.services.Services;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;

public class RetrofitUserService implements Service {
    private final SecureEndpoint secureEndpoint;
    private final InsecureEndpoint insecureEndpoint;

    public RetrofitUserService() {
        this.secureEndpoint = Services.getInstance().getSecureRestAdapter().create(SecureEndpoint.class);
        this.insecureEndpoint = Services.getInstance().getInsecureRestAdapter().create(InsecureEndpoint.class);
    }

    public SecureEndpoint getSecureEndpoint() {
        return secureEndpoint;
    }

    public InsecureEndpoint getInsecureEndpoint() {
        return insecureEndpoint;
    }

    public interface SecureEndpoint {
        @GET("/user")
        UserModel getUser();

        @PUT("/user")
        UserModel putUser(@Body UserModel model);

        @DELETE("/user")
        Response deleteUser();
    }

    public interface InsecureEndpoint {
        @POST("/user")
        UserModel postUser(@Body UserModel model);
    }


}
