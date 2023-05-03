package org.rdtif.zaxslackbot;

import org.junit.jupiter.api.Test;
import software.amazon.awscdk.App;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.assertions.Template;

class ZaxBotCDKStackTest {
    @Test
    public void createsLambdaFunction() {
        App app = new App();
        Stack stack = new ZaxBotCDKStack(app, "CDKStack", null);

        Template template = Template.fromStack(stack);
        template.resourceCountIs("AWS::Lambda::Function", 1);
    }
}
