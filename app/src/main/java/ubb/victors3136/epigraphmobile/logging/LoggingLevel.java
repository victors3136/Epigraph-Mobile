package ubb.victors3136.epigraphmobile.logging;

import androidx.annotation.NonNull;

public enum LoggingLevel {
    INFO,
    WARN,
    ERROR;

    @NonNull
    @Override
    public String toString(){
        return switch (this){
            case INFO -> "[I]";
            case WARN -> "[W]";
            case ERROR -> "[E]";
        };
    }
}
