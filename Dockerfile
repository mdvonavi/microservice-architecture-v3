FROM eclipse-temurin
WORKDIR /opt/app  
COPY demo/build/libs/demo-0.0.1-SNAPSHOT.jar app.jar  
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]  