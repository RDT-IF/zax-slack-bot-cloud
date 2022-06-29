package org.rdtif.zaxslackbot.slack;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.jetty.SlackAppServer;
import com.slack.api.methods.MethodsClient;
import com.slack.api.model.event.AppMentionEvent;
import org.rdtif.zaxslackbot.ZaxSlackBotConfiguration;
import org.rdtif.zaxslackbot.interpreter.Interpreter;
import org.rdtif.zaxslackbot.userinterface.InputState;

import javax.inject.Singleton;

public class SlackApplicationModule extends AbstractModule {
    @Provides
    @Singleton
    AppConfig providesAppConfig(ZaxSlackBotConfiguration configuration) {
        return AppConfig.builder()
                .singleTeamBotToken(configuration.getApiToken())
                .clientSecret(configuration.getSlackClientSecret())
                .signingSecret(configuration.getSlackSigningSecret())
                .build();
    }

    @Provides
    @Singleton
    App providesApp(AppConfig appConfig, Interpreter interpreter, InputState inputState) {
        App app = new App(appConfig);
        // TODO: Write test for this.
        app.event(AppMentionEvent.class, new AppMentionHandler(interpreter));
        app.blockAction("input-window", new InputWindowBlockActionHandler(inputState));
        return app;
    }

    @Provides
    @Singleton
    SlackAppServer providesSlackAppServer(App app) {
        return new SlackAppServer(app);
    }

    @Provides
    @Singleton
    MethodsClient providesMethodsClient(App app) {
        return app.client();
    }
}
