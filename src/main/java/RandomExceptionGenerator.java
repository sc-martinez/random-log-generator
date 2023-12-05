import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cloudwatchlogs.CloudWatchLogsClient;
import software.amazon.awssdk.services.cloudwatchlogs.model.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
/**
 * RandomExceptionGenerator class generates random logs to be fed into AWS CloudWatch Logs.
 * It fetches AWS configuration details from the aws.properties file and continuously generates
 * random log messages, associates them with random exceptions, and sends them to CloudWatch Logs.
 */
public class RandomExceptionGenerator {

    // File path for AWS properties
    private static final String PROPERTIES_FILE = "aws.properties";

    // Variables to store AWS configurations
    private static String LOG_GROUP_NAME;
    private static String LOG_STREAM_NAME;
    private static String REGION;

    /**
     * Loads AWS configuration properties from the aws.properties file.
     * Reads the log group name, log stream name, and AWS region.
     */
    private static void loadProperties() {
        Properties properties = new Properties();
        try {
            FileInputStream fis = new FileInputStream(PROPERTIES_FILE);
            properties.load(fis);
            LOG_GROUP_NAME = properties.getProperty("aws.logGroupName");
            LOG_STREAM_NAME = properties.getProperty("aws.logGroupName");
            REGION = properties.getProperty("aws.region");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Main method to initialize and start generating random logs for CloudWatch.
     * Connects to AWS CloudWatch Logs using AWS SDK, creates log groups and streams,
     * and continuously sends random log messages to CloudWatch Logs.
     */
    public static void main(String[] args) {
        loadProperties();

        CloudWatchLogsClient logsClient = CloudWatchLogsClient.builder()
                .region(Region.of(REGION)) // AWS region
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();

        createLogGroupAndStream(logsClient);

        while (true) {
            String exceptionType = ExceptionMessages.getRandomExceptionType();
            String message = generateRandomLog(exceptionType);

            logToCloudWatch(logsClient, message);

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Generates a random log message with associated log level and exception type.
     *
     * @param exceptionType The type of exception associated with the log message.
     * @return A formatted random log message.
     */
    private static String generateRandomLog(String exceptionType) {
        String logLevel = ExceptionMessages.getRandomLogLevel();
        String message = ExceptionMessages.getRandomMessage();
        return "[" + logLevel + "] - [" + exceptionType + "] - " + message;
    }

    /**
     * Creates a log group and log stream in AWS CloudWatch Logs.
     *
     * @param logsClient CloudWatchLogsClient instance.
     */
    private static void createLogGroupAndStream(CloudWatchLogsClient logsClient) {
        try {
            logsClient.createLogGroup(CreateLogGroupRequest.builder().logGroupName(LOG_GROUP_NAME).build());
            logsClient.createLogStream(CreateLogStreamRequest.builder()
                    .logGroupName(LOG_GROUP_NAME)
                    .logStreamName(LOG_STREAM_NAME)
                    .build());

            System.out.println("Log groups and stream have been created successfully");
        } catch (ResourceAlreadyExistsException ex) {
            System.out.println(LOG_GROUP_NAME + " " + "Already exists as a valid log group..");
        }
    }

    /**
     * Sends log events to AWS CloudWatch Logs.
     *
     * @param logsClient  CloudWatchLogsClient instance.
     * @param logMessage  Log message to be sent.
     */
    private static void logToCloudWatch(CloudWatchLogsClient logsClient, String logMessage) {
        List<InputLogEvent> logEvents = new ArrayList<>();
        logEvents.add(InputLogEvent.builder()
                .message(logMessage)
                .timestamp(Instant.now().toEpochMilli())
                .build());

        PutLogEventsRequest putLogEventsRequest = PutLogEventsRequest.builder()
                .logGroupName(LOG_GROUP_NAME)
                .logStreamName(LOG_STREAM_NAME)
                .logEvents(logEvents)
                .build();

        PutLogEventsResponse response = logsClient.putLogEvents(putLogEventsRequest);

        if (response.rejectedLogEventsInfo() != null) {
            System.err.println("Some log events were rejected: " + response.rejectedLogEventsInfo());
        }
        System.out.println("Some log events were accepted: " + response.toString());
    }
}
