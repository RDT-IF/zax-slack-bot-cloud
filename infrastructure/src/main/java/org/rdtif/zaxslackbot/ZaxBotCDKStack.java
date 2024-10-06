package org.rdtif.zaxslackbot;

import software.amazon.awscdk.Duration;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.apigateway.LambdaIntegration;
import software.amazon.awscdk.services.apigateway.Method;
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
        Map<String, String> environment = Collections.singletonMap(SharedConstants.SIGNING_SECRET_ENVIRONMENT_VARIABLE, slackSigningSecret);
        SingletonFunction function = SingletonFunction.Builder.create(this, "zax-bot-lambda")
                .description("ZaxBot Lambda")
                .runtime(Runtime.JAVA_11)
                .timeout(Duration.minutes(15))
                .code(Code.fromAsset("../aws-lambda-bot/build/libs/aws-lambda-bot.jar"))
                .handler("org.rdtif.zaxslackbot.ZaxBotRequestHandler")
                .environment(environment)
                .uuid(UUID.randomUUID().toString())
                .memorySize(512)
                .build();

        LambdaIntegration simpleIntegration = LambdaIntegration.Builder.create(function).build();
        RestApi api = RestApi.Builder.create(this, "zax-bot-gateway")
                .restApiName("ZaxBot API Gateway")
                .description("ZaxBot API Gateway")
                .defaultIntegration(simpleIntegration)
                .build();

        MethodOptions options = MethodOptions.builder().apiKeyRequired(false).build();
        Resource eventsResource = api.getRoot().addResource("slack").addResource("events");
        Method eventsPostMethod = eventsResource.addMethod("POST", simpleIntegration, options);
        eventsPostMethod.addMethodResponse(MethodResponse.builder().statusCode("200").build());
        eventsPostMethod.addMethodResponse(MethodResponse.builder().statusCode("400").build());
        eventsPostMethod.addMethodResponse(MethodResponse.builder().statusCode("401").build());
    }
}
