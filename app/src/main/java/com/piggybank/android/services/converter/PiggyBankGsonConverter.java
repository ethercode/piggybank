package com.piggybank.android.services.converter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.piggybank.android.services.converter.deserializers.IsoDateJsonDeserializer;
import com.piggybank.android.services.types.IsoDate;

import retrofit.converter.GsonConverter;

public class PiggyBankGsonConverter {
    private static final PiggyBankGsonConverter ourInstance = new PiggyBankGsonConverter();
    private final GsonConverter gsonConverter;

    private PiggyBankGsonConverter() {
        GsonBuilder builder = new GsonBuilder();

        builder.registerTypeAdapter(IsoDate.class, new IsoDateJsonDeserializer());

        Gson gson = builder.create();

        gsonConverter = new GsonConverter(gson);
    }

    public static PiggyBankGsonConverter getInstance() {
        return ourInstance;
    }

    public GsonConverter getGsonConverter() {
        return gsonConverter;
    }
}

