package org.rdtif.zaxslackbot;

import org.junit.jupiter.api.Test;
import software.amazon.awscdk.App;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.assertions.Template;

import java.util.Collections;

class ZaxBotCDKStackTest {
    @Test
    void createsLambdaFunction() {
        App app = new App();

        Stack stack = new ZaxBotCDKStack(app, "CDKStack", null);

        Template template = Template.fromStack(stack);
        template.resourceCountIs("AWS::Lambda::Function", 1);
    }

    @Test
    void assignsDescriptionToFunction() {
        App app = new App();

        Stack stack = new ZaxBotCDKStack(app, "CDKStack", null);

        Template template = Template.fromStack(stack);
        template.hasResourceProperties("AWS::Lambda::Function", Collections.singletonMap("Description", "Zax Bot Lambda"));
    }

    @Test
    void assignsCorrectHandler() {
        App app = new App();

        Stack stack = new ZaxBotCDKStack(app, "CDKStack", null);

        Template template = Template.fromStack(stack);
        template.hasResourceProperties("AWS::Lambda::Function", Collections.singletonMap("Handler", "org.rdtif.zaxslackbot.ZaxBotRequestHandler"));
    }

    @Test
    void assignsCorrectRuntime() {
        App app = new App();

        Stack stack = new ZaxBotCDKStack(app, "CDKStack", null);

        Template template = Template.fromStack(stack);
        template.hasResourceProperties("AWS::Lambda::Function", Collections.singletonMap("Runtime", "java11"));
    }

    @Test
    void configuresFunctionUrl() {
        App app = new App();

        Stack stack = new ZaxBotCDKStack(app, "CDKStack", null);

        Template template = Template.fromStack(stack);
        template.resourceCountIs("AWS::Lambda::Url", 1);
    }

    @Test
    void functionUrlShouldBePublic() {
        App app = new App();

        Stack stack = new ZaxBotCDKStack(app, "CDKStack", null);

        Template template = Template.fromStack(stack);
        template.hasResourceProperties("AWS::Lambda::Url", Collections.singletonMap("AuthType", "NONE"));
    }
}
