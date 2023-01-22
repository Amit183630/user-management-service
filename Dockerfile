FROM openjdk:11
EXPOSE 8080
ADD target/user-management-service.jar user-management-service.jar
ENTRYPOINT ["java","-jar","/user-management-service.jar"]