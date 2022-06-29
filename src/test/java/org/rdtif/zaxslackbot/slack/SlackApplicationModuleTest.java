package org.rdtif.zaxslackbot.slack;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.jetty.SlackAppServer;
import org.junit.jupiter.api.Test;
import org.rdtif.zaxslackbot.ZaxSlackBotConfiguration;

import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

class SlackApplicationModuleTest {
    @Test
    void providesAppConfig() {
        AppConfig config = new SlackApplicationModule().providesAppConfig(createConfiguration());

        assertThat(config, notNullValue());
    }

    @Test
    void providesAppConfigWithApiToken() {
        AppConfig config = new SlackApplicationModule().providesAppConfig(createConfiguration());

        assertThat(config.getSingleTeamBotToken(), equalTo("apiToken"));
    }

    @Test
    void providesAppConfigWithSlackClientSecret() {
        AppConfig config = new SlackApplicationModule().providesAppConfig(createConfiguration());

        assertThat(config.getClientSecret(), equalTo("slackClientSecret"));
    }

    @Test
    void providesAppConfigWithSlackSigningSecret() {
        AppConfig config = new SlackApplicationModule().providesAppConfig(createConfiguration());

        assertThat(config.getSigningSecret(), equalTo("slackSigningSecret"));
    }

    @Test
    void providesApplication() {
        App app = new SlackApplicationModule().providesApp(new AppConfig(), null, null);

        assertThat(app, notNullValue());
    }

    @Test
    void providesApplicationWithConfig() {
        App app = new SlackApplicationModule().providesApp(AppConfig.builder().clientSecret("clientSecret").build(), null, null);

        assertThat(app.config().getClientSecret(), equalTo("clientSecret"));
    }

    @Test
    void providesAppServer() {
        SlackAppServer server = new SlackApplicationModule().providesSlackAppServer(new App());

        assertThat(server, notNullValue());
    }

    private ZaxSlackBotConfiguration createConfiguration() {
        Properties properties = new Properties();
        properties.setProperty("api-token", "apiToken");
        properties.setProperty("slack-client-secret", "slackClientSecret");
        properties.setProperty("slack-signing-secret", "slackSigningSecret");
        return new ZaxSlackBotConfiguration(properties);
    }
}