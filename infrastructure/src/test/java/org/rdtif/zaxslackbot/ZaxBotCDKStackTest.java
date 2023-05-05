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
        template.hasResourceProperties("AWS::Lambda::Function", Collections.singletonMap("Description", "ZaxBot Lambda"));
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
    void createAPIGateway() {
        App app = new App();

        Stack stack = new ZaxBotCDKStack(app, "CDKStack", null);

        Template template = Template.fromStack(stack);
        template.resourceCountIs("AWS::ApiGateway::RestApi", 1);
    }

    @Test
    void supplyPOSTMethod() {
        App app = new App();

        Stack stack = new ZaxBotCDKStack(app, "CDKStack", null);

        Template template = Template.fromStack(stack);
        template.resourceCountIs("AWS::ApiGateway::Method", 1);
        template.hasResourceProperties("AWS::ApiGateway::Method", Collections.singletonMap("HttpMethod", "POST"));
    }

    @Test
    void assignsNameToGateway() {
        App app = new App();

        Stack stack = new ZaxBotCDKStack(app, "CDKStack", null);

        Template template = Template.fromStack(stack);
        template.hasResourceProperties("AWS::ApiGateway::RestApi", Collections.singletonMap("Name", "ZaxBot API Gateway"));
    }

    @Test
    void assignsDescriptionToGateway() {
        App app = new App();

        Stack stack = new ZaxBotCDKStack(app, "CDKStack", null);

        Template template = Template.fromStack(stack);
        template.hasResourceProperties("AWS::ApiGateway::RestApi", Collections.singletonMap("Description", "ZaxBot API Gateway"));
    }
}
