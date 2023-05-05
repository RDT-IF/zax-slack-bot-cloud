package org.rdtif.zaxslackbot;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.apigateway.LambdaIntegration;
import software.amazon.awscdk.services.apigateway.MethodOptions;
import software.amazon.awscdk.services.apigateway.MethodResponse;
import software.amazon.awscdk.services.apigateway.Resource;
import software.amazon.awscdk.services.apigateway.RestApi;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Runtime;
import software.amazon.awscdk.services.lambda.SingletonFunction;
import software.constructs.Construct;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class ZaxBotCDKStack extends Stack {
    public ZaxBotCDKStack(Construct scope, String id, StackProps properties, String slackSigningSecret) {
        super(scope, id, properties);
        Map<String, String> environment = Collections.singletonMap("SLACK_SIGNING_SECRET", slackSigningSecret);
        SingletonFunction function = SingletonFunction.Builder.create(this, "zax-bot-lambda")
                .description("ZaxBot Lambda")
                .runtime(Runtime.JAVA_11)
                .code(Code.fromAsset("../bot/build/libs/bot.jar"))
                .handler("org.rdtif.zaxslackbot.ZaxBotRequestHandler")
                .environment(environment)
                .uuid(UUID.randomUUID().toString())
                .build();
        LambdaIntegration simpleIntegration = LambdaIntegration.Builder.create(function).build();
        RestApi api = RestApi.Builder.create(this, "zax-bot-gateway")
                .restApiName("ZaxBot API Gateway")
                .description("ZaxBot API Gateway")
                .defaultIntegration(simpleIntegration)
                .build();
        Resource slackPath = api.getRoot().addResource("slack");
        Resource eventsEndpoint = slackPath.addResource("events");
        MethodOptions options = MethodOptions.builder().apiKeyRequired(false).build();
        eventsEndpoint.addMethod("POST", simpleIntegration, options).addMethodResponse(MethodResponse.builder().statusCode("200").build());
    }
}
