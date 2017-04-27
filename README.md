Spring-boot-template [![CircleCI](https://circleci.com/gh/DiUS/spring-boot-template.svg?style=svg)](https://circleci.com/gh/DiUS/spring-boot-template)
=====================================
This is an example of SpringBoot application with basic CRUD, integrating with consumer driven contract testing PACT. The project now includes 2 separate modules/services:
* account-api (Provider)
* account-client (Consumer)

## Test API using PACT

### Step 1: Generate Pact File
	./gradlew :account-client:test

Gradle task will generate `pacts/Account_Consumer-Account_Provider.json`

### Step 2: Run SpringBoot Application
	./gradlew bootRun

### Step 3: Verify Pact
Open second terminal window and run following command:

	./gradlew pactVerify

If everything configured properly, you will see the following output in the terminal

	```Verifying a pact between Account_Consumer and accountProvider
     [Using file /Users/tuanpham/works/dius/others/spring-boot-template/pacts/Account_Consumer-Account_Provider.json]
     Given Account_State
            WARNING: State Change ignored as there is no stateChange URL
     Get all Accounts request
       returns a response which
         has status code 200 (OK)
         includes headers
           "Content-Type" with value "application/json" (OK)
         has a matching body (OK)```