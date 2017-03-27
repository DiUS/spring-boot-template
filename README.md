Spring-boot-template
=====================================
This is an example of Springboot application with basic CRUD. 

## Run Springboot app
* Run `./gradlew run` 
* Open `http://localhost:8080/` in the browser


## Useful curl commands
* Show all accounts `curl -v -X GET -H 'Content-Type: application/json' 'http://localhost:8080/accounts'`
* Create new account `curl -v -X POST -H 'Content-Type: application/json' 'http://localhost:8080/accounts' -d "{\"name\":\"Tuan\", \"address\": \"99 Queen st\"}"`
* Get account by ID `curl -v -X GET -H 'Content-Type: application/json' 'http://localhost:8080/accounts/1'`
* Delete account `curl -v -X DELETE -H 'Content-Type: application/json' 'http://localhost:8080/accounts/1'`
* Update an account `curl -v -X PUT -H 'Content-Type: application/json' 'http://localhost:8080/accounts/1' -d "{\"name\":\"Modified Name\"}"`