# zax-slack-bot-cloud
An UI layer wrapped around Zax (a Java Z-Interpreter) designed for a cloud environment

## TODO
* Confirm/implement a mechanism by which the bot.jar is generated before infrastructure builds
* Storing state
  * Add an S3 bucket
  * Use the S3 bucket to store the state of the lambda
    * Hm. How will connection strings work?
    
