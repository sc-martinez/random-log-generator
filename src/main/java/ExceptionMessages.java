import java.util.Random;
// Exception import
import java.lang.NullPointerException;
import java.lang.ArrayIndexOutOfBoundsException;
import java.lang.ArithmeticException;
import java.lang.NumberFormatException;
import java.lang.IllegalArgumentException;
import java.lang.IllegalStateException;
import java.lang.ClassCastException;
import java.util.NoSuchElementException;
import java.lang.IndexOutOfBoundsException;
import java.lang.UnsupportedOperationException;
import java.util.ConcurrentModificationException;
import java.lang.NegativeArraySizeException;
import java.util.EmptyStackException;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.time.DateTimeException;
import java.util.MissingResourceException;
import java.lang.StringIndexOutOfBoundsException;
import java.lang.TypeNotPresentException;
import java.util.regex.PatternSyntaxException;
import java.io.UncheckedIOException;
import java.util.zip.DataFormatException;
import java.security.InvalidKeyException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.BadPaddingException;
import java.security.DigestException;
import java.security.spec.InvalidParameterSpecException;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.TooManyListenersException;
import java.io.InvalidObjectException;
import java.io.StreamCorruptedException;
import java.io.WriteAbortedException;
import java.io.ObjectStreamException;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.InterruptedIOException;
import java.net.BindException;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.PortUnreachableException;
import java.net.ProtocolException;
import javax.net.ssl.SSLHandshakeException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

/**
 * ExceptionMessages class manages the exception types, log levels, and random messages
 * used by the RandomExceptionGenerator class to generate logs for AWS CloudWatch Logs.
 */
class ExceptionMessages {

    // Array of log levels
    private static final String[] LOG_LEVELS = {
            "ERROR",
            "WARNING",
            "INFO"
    };

    // Array of predefined exception types
    public static final String[] EXCEPTIONS = {
            "java.lang.NullPointerException",
            "java.lang.ArrayIndexOutOfBoundsException",
            "java.lang.ArithmeticException",
            "java.lang.NumberFormatException",
            "java.lang.IllegalArgumentException",
            "java.lang.IllegalStateException",
            "java.lang.ClassCastException",
            "java.util.NoSuchElementException",
            "java.lang.IndexOutOfBoundsException",
            "java.lang.UnsupportedOperationException",
            "java.util.ConcurrentModificationException",
            "java.lang.NegativeArraySizeException",
            "java.util.EmptyStackException",
            "java.nio.BufferOverflowException",
            "java.nio.BufferUnderflowException",
            "java.time.DateTimeException",
            "java.util.MissingResourceException",
            "java.lang.StringIndexOutOfBoundsException",
            "java.lang.TypeNotPresentException",
            "java.util.regex.PatternSyntaxException",
            "java.io.UncheckedIOException",
            "java.util.zip.DataFormatException",
            "java.security.InvalidKeyException",
            "java.security.InvalidAlgorithmParameterException",
            "java.security.NoSuchAlgorithmException",
            "java.security.NoSuchProviderException",
            "java.security.spec.InvalidKeySpecException",
            "javax.crypto.IllegalBlockSizeException",
            "javax.crypto.BadPaddingException",
            "java.security.DigestException",
            "java.security.spec.InvalidParameterSpecException",
            "java.nio.charset.IllegalCharsetNameException",
            "java.nio.charset.UnsupportedCharsetException",
            "java.util.TooManyListenersException",
            "java.io.InvalidObjectException",
            "java.io.StreamCorruptedException",
            "java.io.WriteAbortedException",
            "java.io.ObjectStreamException",
            "java.io.EOFException",
            "java.io.FileNotFoundException",
            "java.io.InterruptedIOException",
            "java.net.BindException",
            "java.net.ConnectException",
            "java.net.NoRouteToHostException",
            "java.net.PortUnreachableException",
            "java.net.ProtocolException",
            "javax.net.ssl.SSLHandshakeException",
            "java.net.MalformedURLException",
            "java.net.URISyntaxException",
    };

    // Array of predefined error messages
    private static final String[] MESSAGES = {
            "Error occurred in module A",
            "Unexpected input detected",
            "Operation failed due to unknown reasons",
            "Resource not found",
            "Network connection lost",
            "File corrupted",
            "Authentication failed",
            "Database connection timed out",
            "Invalid input parameter",
            "Service unavailable",
            "Server overloaded",
            "Memory allocation error",
            "Permission denied",
            "File not accessible",
            "Configuration error",
            "Data integrity compromised",
            "Unexpected behavior detected",
            "Dependency not available",
            "API request failed",
            "Request timeout",
            "Unexpected server response",
            "Critical system error",
            "Session expired",
            "Task interrupted",
            "Processing error",
            "Input validation error",
            "Invalid credentials provided",
            "Resource exhausted",
            "Concurrency issue detected",
            "Hardware malfunction detected",
            "Software bug detected",
    };

    /**
     * Retrieves a random error message from the predefined list.
     *
     * @return A randomly selected error message.
     */
    public static String getRandomMessage() {
        Random random = new Random();
        int messageIndex = random.nextInt(MESSAGES.length);
        return MESSAGES[messageIndex];
    }

    /**
     * Retrieves a random exception type from the predefined list.
     *
     * @return A randomly selected exception type.
     */
    public static String getRandomExceptionType() {
        Random random = new Random();
        int exceptionIndex = random.nextInt(EXCEPTIONS.length);
        return EXCEPTIONS[exceptionIndex];
    }

    /**
     * Dynamically creates an exception object based on the provided exception type and message.
     * Tries to instantiate the specified exception type; if unsuccessful, creates a RuntimeException.
     *
     * @param exceptionType The type of exception to create.
     * @param message       The error message associated with the exception.
     * @return An exception object based on the specified type and message.
     */
    public static Exception createException(String exceptionType, String message) {
        try {
            Class<?> exceptionClass = Class.forName(exceptionType);
            return (Exception) exceptionClass.getConstructor(String.class).newInstance(message);
        } catch (Exception e) {
            return new RuntimeException(message);
        }
    }

    /**
     * Retrieves a random log level from the predefined list of log levels.
     *
     * @return A randomly selected log level.
     */
    public static String getRandomLogLevel() {
        Random random = new Random();
        int logLevelIndex = random.nextInt(LOG_LEVELS.length);
        return LOG_LEVELS[logLevelIndex];
    }
}