package org.rdtif.zaxslackbot;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.inject.Inject;

class ZaxLogger {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Context context;

    @Inject
    ZaxLogger(Context context) {
        this.context = context;
    }

    void log(String message) {
        context.getLogger().log(message);
    }

    void log(String message, Object object) {
        context.getLogger().log(message + gson.toJson(object));
    }

    void log(Object object) {
        context.getLogger().log(gson.toJson(object));
    }
}
