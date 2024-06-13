[![Java CI with Maven](https://github.com/inBrackets/CoffeeShop/actions/workflows/maven.yml/badge.svg)](https://github.com/inBrackets/CoffeeShop/actions/workflows/maven.yml)
# Coffee Shop

## Useful side tools

### Maven wrapper
Runs maven plugins without installing maven. To run, use the following template:
```
./mvnw [plugin-name]:plugin-method
```
Example way for running the Spring boot app:
```
./mvnw spring-boot:run
```

### JPS (Java process status)
Use the jps tool to query running Java™ processes. The tool shows information for every Java process that is owned by the current user ID on the current host. more info: https://eclipse.dev/openj9/docs/tool_jps/
```
jps -h
```

### Swagger
```
http://localhost:8080/swagger-ui/index.html
```

## Releases

### master
* Add support for H2 DB
    * H2 console should be available under `spring.h2.console.path` endpoint defined in `application.properties`

### v1.0.0
* Simple REST API with no DB
* Defined Banner
* Defined GH Actions
