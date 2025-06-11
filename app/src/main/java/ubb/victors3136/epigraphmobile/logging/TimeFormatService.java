package ubb.victors3136.epigraphmobile.logging;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeFormatService{
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    public static String now(){
        return LocalTime.now().format(timeFormatter);
    }
}
