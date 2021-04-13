Spring-boot-template [![CircleCI](https://circleci.com/gh/DiUS/spring-boot-template.svg?style=shield&&circle-token=91b8d409121e015a37c80295efae97976d1a5924)](https://circleci.com/gh/DiUS/spring-boot-template)
=====================================
This is an example of SpringBoot application with basic CRUD, integrating with consumer driven contract testing PACT. The project now includes 2 separate modules/services:
* account-api (Provider)
* account-client (Consumer)

## Test API using PACT

Go to the project folder.

### Step 1: Generate Pact File

	./gradlew :account-client:test

Gradle task will generate `pacts/Account_Consumer-Account_Provider.json`

### Step 2: Verify Pact
You can verify the pact by launching the provider explicitly, 
e.g. during the development process
or by making gradle launch your provider application. 


#### Step 2a: Let gradle launch the provider-application

To verify pact, run below command. 
Gradle will start Springboot app first, verify pact, then close the app.

	./gradlew :account-api:pactVerify -PdoLaunchProvider

#### Step 2b: Launch the application explicitely

Start the Springboot app first. 
You can verify if the app is running by opening `http://localhost:8080/accounts/`

	./gradlew :account-api:bootRun



After the app is running - verify pact on the running app.

	./gradlew :account-api:pactVerify 

	
### Step 3: Output


If everything configured properly, you will see the following output in the terminal

	Verifying a pact between Account_Consumer and accountProvider
         [Using file /Users/tuanpham/works/dius/others/spring-boot-template/pacts/Account_Consumer-Account_Provider.json]
         Create new Accounts
           returns a response which
             has status code 201 (OK)
             has a matching body (OK)
         Delete Account
           returns a response which
             has status code 200 (OK)
             has a matching body (OK)
         Find all Accounts
           returns a response which
             has status code 200 (OK)
             includes headers
               "Content-Type" with value "application/json" (OK)
             has a matching body (OK)
         Get Account By ID
           returns a response which
             has status code 200 (OK)
             includes headers
               "Content-Type" with value "application/json" (OK)
             has a matching body (OK)
       :account-api:stopProvider
       :account-api:pactVerify
