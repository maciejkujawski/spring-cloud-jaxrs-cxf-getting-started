# Introduction

Holds configuration of services in single git location. Changing configuration requires only push to master branch (default place from where configuration is
 pulled). Common configuration is hold in `application.properties/yml` file. Application specific configuration is kept in `{application_name}
 .properties/yml` files. So for example: `microservice2.properties` holds configuration of microservice called `microservice2`.

Running Spring microservice with some profile, ex: `production`, enables to configure your microservice for given environment only. You can have file: 
`microservice2-production.properties` which will override properties hold in `microservice2.properties` and `application.properties` when your microservice
 will be run with Spring's Profile: `production` and it's name is `microservice2`.

Application specific properties by default should be kept on root directory of git repository but in this example this is changed in application.yml of config-server.