package org.rdtif.zaxslackbot;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.apigateway.LambdaIntegration;
import software.amazon.awscdk.services.apigateway.MethodResponse;
import software.amazon.awscdk.services.apigateway.RestApi;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Runtime;
import software.amazon.awscdk.services.lambda.SingletonFunction;
import software.constructs.Construct;

import java.util.UUID;

public class ZaxBotCDKStack extends Stack {
    public ZaxBotCDKStack(Construct scope, String id, StackProps properties) {
        super(scope, id, properties);
        SingletonFunction function = SingletonFunction.Builder.create(this, "zax-bot-lambda")
                .description("ZaxBot Lambda")
                .runtime(Runtime.JAVA_11)
                .code(Code.fromAsset("../bot/build/libs/bot.jar"))
                .handler("org.rdtif.zaxslackbot.ZaxBotRequestHandler")
                .uuid(UUID.randomUUID().toString())
                .build();
        LambdaIntegration simpleIntegration = LambdaIntegration.Builder.create(function).build();
        RestApi api = RestApi.Builder.create(this, "zax-bot-gateway")
                .restApiName("ZaxBot API Gateway")
                .description("ZaxBot API Gateway")
                .defaultIntegration(simpleIntegration)
                .build();
        api.getRoot().addMethod("POST").addMethodResponse(MethodResponse.builder().statusCode("200").build());
    }
}
