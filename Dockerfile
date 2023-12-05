FROM openjdk:11

# Set the working directory in the container
WORKDIR /app

COPY target/log-generator-1.0.jar /app/log-generator-1.0.jar

COPY aws.properties /app/aws.properties

# Set AWS credentials and region as environment variables
ENV AWS_ACCESS_KEY_ID=<Your_Key_ID>
ENV AWS_SECRET_ACCESS_KEY=<Your_secret>
ENV AWS_DEFAULT_REGION=<Your_Region>

# Run the Java application
CMD ["java", "-jar", "log-generator-1.0.jar"]
