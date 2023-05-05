# Description
A Slack UI layer wrapped around Zax (a Java Z-Interpreter) designed for a cloud environment

# Deployment Instructions
AWS CDK will need to be available on your computer before deploying.

* Set the following environment variables:
  * CDK_DEFAULT_REGION: The AWS region to which you wish to deploy.
  * CDK_DEFAULT_ACCOUNT: The AWS account to which you wish to deploy.
  * ZAXBOT_SLACK_SIGNING_SECRET: The Slack signing secret for ZaxBot
* `cdk diff` to review the changes the deployment will make.
* `cdk deploy` to deploy.

# Useful commands
* `cdk ls`          list all stacks in the app
* `cdk synth`       emits the synthesized CloudFormation template
* `cdk deploy`      deploy this stack to your default AWS account/region
* `cdk diff`        compare deployed stack with current state
* `cdk docs`        open CDK documentation

# TODO
* Configuration sufficient that conversation with the bot reaches the function.
  * Identify solution for storing the secret hash for Slack
  * Respond to Slack's url_verification event
  * Verify the authenticity of the incoming event in the code
  * ...
* Storing state
  * Add an S3 bucket
  * Use the S3 bucket to store the state of the lambda
    * Hm. How will connection strings work?
