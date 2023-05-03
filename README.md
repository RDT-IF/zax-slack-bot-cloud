# zax-slack-bot-cloud
An UI layer wrapped around Zax (a Java Z-Interpreter) designed for a cloud environment

# Deployment Instructions
AWS CDK will need to be available on your computer before deploying.

* Set the following environment variables:
  * CDK_DEFAULT_REGION: The AWS region to which you wish to deploy.
  * CDK_DEFAULT_ACCOUNT: The AWS account to which you wish to deploy.
* `cdk diff` to review the changes the deployment will make.
* `cdk deploy` to deploy.

## TODO
* Configuration sufficient that conversation with the bot reaches the function.
* Storing state
  * Add an S3 bucket
  * Use the S3 bucket to store the state of the lambda
    * Hm. How will connection strings work?
