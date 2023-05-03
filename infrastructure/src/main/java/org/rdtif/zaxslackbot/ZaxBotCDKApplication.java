package org.rdtif.zaxslackbot;

import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.StackProps;

public class ZaxBotCDKApplication {
    public static void main(String... arguments) {
        Environment environment = Environment.builder()
                .account(System.getenv("CDK_DEFAULT_ACCOUNT"))
                .region(System.getenv("CDK_DEFAULT_REGION"))
                .build();

        App application = new App();
        new ZaxBotCDKStack(application, "ZaxBotStack", StackProps.builder().env(environment).build());
        application.synth();
    }
}
