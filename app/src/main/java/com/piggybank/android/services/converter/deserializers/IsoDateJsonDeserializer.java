package com.piggybank.android.services.converter.deserializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.piggybank.android.services.types.IsoDate;

import java.lang.reflect.Type;

public class IsoDateJsonDeserializer implements JsonDeserializer<IsoDate> {
    public IsoDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        return IsoDate.convert(json.getAsJsonPrimitive().getAsString());
    }
}
