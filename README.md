FrameworkForAlgorithms!
==========

Framework for running SH and R scripts over AWS Lambda

----------
  

Why :
-------------

Algorithm framework allows teams to develop, deploy and execute algorithms on AWS lambda, independent from the parent applications  

Existing state:  
Teams write algorithms specific to their application even when there is scope for making them generic so that other teams can use.  
This is happening due to lack of proper guidelines and extra effort to keep the code generic.  
Backward compatibility of various versions of the Application is an overhead.  
Testing of an Algorithm requires the entire application to be up and running.  
Algorithms are not reusable. Other teams are spending time on designing the same Algorithm again. After a while we will have multiple versions of the same Algorithm in different places in different states.  
If an Application fixes a bug in an Algorithm, other teams doesn’t get the benefit of it.  


Advantages :
-------------

Decoupling of Algorithms from the parent application.    
Zero Down time.  
Versioning of Algorithms with ease.  
Reuse of existing Algorithms.  
Application teams need not worry about code to execute lambda.  
Any changes to lambda in future need to be fixed only in Algorithm Framework code.  
Allows wiring of different Algorithms to build custom/new workflows.  
Supports Algorithms written in both Java and R.  
Cost savings for applications due to the inherent benefits of lambda.  


Input JSON Format :
-------------
This lambda takes JSON parameters as input, please find sample below.  

```json
{
  "downloads": [
    "1st AWS S3 URL to be downloaded for execution",
    "2nd AWS S3 URL to be downloaded  for execution",
    "... AWS S3 URL to be downloaded  for execution",
    "nth AWS S3 URL to be downloaded  for execution",
  ],
  "dependency" : "AWS S3 URL for the algorithm JAR, empty in case of R",
  "script" : "name of the script to be executed",
  "inputs": [
    "1st input to the script",
    "2nd input to the script",
    "... input to the script",
    "nth input to the script",
  ],
  "outputBucket": "name of destination bucket(should exists) in AWS S3 where outputs(if any) needs to be moved to"
}
```


Sections of the JSON :
-------------

downloads    : are anything that needs to be downloaded from S3.  
dependency   : are JAR or ZIP (jar s3 location should be provided)  
script       : is the file to be executed. Based on the extension of script file it is decided which flow to invoke. (JAR or R)  
inputs       : are the parameters which are to be passed to the script. In the order of passing.  
outputBucket : is the bucket name in S3(bucket should exists) where output file(if any) after execution of the script are to be moved.  
