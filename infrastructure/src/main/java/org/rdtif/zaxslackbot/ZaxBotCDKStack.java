package org.rdtif.zaxslackbot;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Runtime;
import software.amazon.awscdk.services.lambda.SingletonFunction;
import software.constructs.Construct;

import java.util.UUID;

public class ZaxBotCDKStack extends Stack {
    private static final String CODE = "def main(event, context):\n" + "    print(\"I'm running!\")\n";

    public ZaxBotCDKStack(Construct scope, String id, StackProps properties) {
        super(scope, id, properties);
        SingletonFunction zaxBot = SingletonFunction.Builder.create(this, "zax-bot-lambda")
                .description("Zax Bot Lambda")
                .code(Code.fromInline(CODE))
                .handler("index.main")
                .runtime(Runtime.PYTHON_3_9)
                .uuid(UUID.randomUUID().toString())
                .build();
    }
}
