package ubb.victors3136.epigraphmobile.logging;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoggingService {
    private static final ExecutorService manager = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private synchronized static void log(LoggingLevel level, String message) {
        manager.submit(() -> System.out.println(TimeFormatService.now() + " " + level + ": " + message));
    }

    public static void info(String message) {
        log(LoggingLevel.INFO, message);
    }

    public static void warn(String message) {
        log(LoggingLevel.WARN, message);
    }

    public static void error(String message) {
        log(LoggingLevel.ERROR, message);
    }
}
