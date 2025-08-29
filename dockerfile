FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# Copy fat jar
COPY build/libs/*.jar app.jar
# Run as non-root (matches k8s securityContext)
USER 1000:1000
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]