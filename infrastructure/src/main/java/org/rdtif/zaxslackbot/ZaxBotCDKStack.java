package org.rdtif.zaxslackbot;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Runtime;
import software.amazon.awscdk.services.lambda.SingletonFunction;
import software.constructs.Construct;

import java.util.UUID;

public class ZaxBotCDKStack extends Stack {
    public ZaxBotCDKStack(Construct scope, String id, StackProps properties) {
        super(scope, id, properties);
        SingletonFunction zaxBot = SingletonFunction.Builder.create(this, "zax-bot-lambda")
                .description("Zax Bot Lambda")
                .runtime(Runtime.JAVA_11)
                .code(Code.fromAsset("../bot/build/libs/bot.jar"))
                .handler("org.rdtif.zaxslackbot.ZaxBotRequestHandler")
                .uuid(UUID.randomUUID().toString())
                .build();
    }
}
