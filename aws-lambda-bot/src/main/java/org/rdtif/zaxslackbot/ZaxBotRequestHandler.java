package org.rdtif.zaxslackbot;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class ZaxBotRequestHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent apiEvent, Context context) {
        try {
            Injector injector = Guice.createInjector(new ZaxBotModule(new ZaxLogger(context)));
            ApiEventHandler apiEventHandler = injector.getInstance(ApiEventHandler.class);
            return apiEventHandler.handle(apiEvent);
        } catch (Exception exception) {
            context.getLogger().log("Exception caught: " + exception.getMessage());
            context.getLogger().log("Stacktrace: " + gson.toJson(exception.getStackTrace()));
            throw exception;
        }
    }
}
