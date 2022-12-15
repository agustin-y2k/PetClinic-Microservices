# Distributed version of the Spring PetClinic Sample Application built with Spring Cloud 


## Arranque sin Docker

Cada microservicio es una aplicación Spring Boot y se puede iniciar localmente usando su IDE o con el comando `../mvnw spring-boot:run`. Tanto Config server como Discovery Server se deben iniciar antes que los demas microservicios (Customers, Vets, Visits and API).
Arrancar Tracing server, Admin server, Grafana y Prometheus es optional.
Si todo sale bien se puede acceder a los servicios mediante las siguientes rutas:
* Discovery Server - http://localhost:8761
* Config Server - http://localhost:8888
* AngularJS frontend (API Gateway) - http://localhost:8080 (NO ANDA) Para consultas via postman usar para **customers-service** http://localhost:8081, para **visits-service** http://localhost:8082 y para **vets-service** http://localhost:8083
* Tracing Server (Zipkin) - http://localhost:9411/zipkin/ 
* Admin Server (Spring Boot Admin) - http://localhost:9090
* Grafana Dashboards - http://localhost:3000
* Prometheus - http://localhost:9091

Puede decirle a **Config Server** que use su repositorio local de Git usando el perfil y la configuración de Spring `native`
`GIT_REPO`

## Arranque utilizando docker
Para iniciar toda la infraestructura con Docker, debe crear imágenes ejecutando `./mvnw clean install -P buildDocker` 
desde la raiz del proyecto. Una vez que las imágenes están listas, puede iniciarlas con
`docker-compose up`.

## Arranque con Docker Compose
Se puede realizar con `docker-compose up` o via `./scripts/run_all.sh`

**Arquitectura Petclinic Microservices**

![Spring Petclinic Microservices architecture](docs/microservices-architecture-diagram.jpg)

### Custom metrics
Spring Boot registra una gran cantidad de métricas centrales: JVM, CPU, Tomcat, Logback...
La configuración automática de Spring Boot permite la instrumentación de solicitudes manejadas por Spring MVC.
Todos esos tres controladores REST `OwnerResource`, `PetResource` y `VisitResource` han sido instrumentados por la anotación `@Timed` Micrómetro a nivel de clase.

* `customers-service`
  * @Timed: `petclinic.owner`
  * @Timed: `petclinic.pet`
* `visits-service`
  * @Timed: `petclinic.visit`


| Componentes                     | Recursos                                                                                                                                                                                              |
|---------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Configuration server            | [Config server properties](spring-petclinic-config-server/src/main/resources/application.yml) and [Configuration repository]                                                                          |
| Service Discovery               | [Eureka server](spring-petclinic-discovery-server) and [Service discovery client](spring-petclinic-vets-service/src/main/java/org/springframework/samples/petclinic/vets/VetsServiceApplication.java) |
| API Gateway                     | [Spring Cloud Gateway starter](spring-petclinic-api-gateway/pom.xml) and [Routing configuration](/spring-petclinic-api-gateway/src/main/resources/application.yml)                                    |
| Docker Compose                  | [Spring Boot with Docker guide](https://spring.io/guides/gs/spring-boot-docker/) and [docker-compose file](docker-compose.yml)                                                                        |
| Circuit Breaker                 | [Resilience4j fallback method](spring-petclinic-api-gateway/src/main/java/org/springframework/samples/petclinic/api/boundary/web/ApiGatewayController.java)                                           |
| Grafana / Prometheus Monitoring | [Micrometer implementation](https://micrometer.io/), [Spring Boot Actuator Production Ready Metrics]                                                                                                  |

