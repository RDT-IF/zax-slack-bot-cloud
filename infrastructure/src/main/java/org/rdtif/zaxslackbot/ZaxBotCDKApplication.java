package org.rdtif.zaxslackbot;

import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.StackProps;

public class ZaxBotCDKApplication {
    public static void main(String... arguments) {
        Environment deploymentEnvironment = Environment.builder()
                .account(System.getenv("CDK_DEFAULT_ACCOUNT"))
                .region(System.getenv("CDK_DEFAULT_REGION"))
                .build();
        StackProps stackProperties = StackProps.builder().env(deploymentEnvironment).build();

        App application = new App();
        new ZaxBotCDKStack(application, "ZaxBotStack", stackProperties, System.getenv(SharedConstants.SIGNING_SECRET_ENVIRONMENT_VARIABLE));
        application.synth();
    }
}
