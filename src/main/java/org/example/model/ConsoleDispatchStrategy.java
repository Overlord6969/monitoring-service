package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableConsoleDispatchStrategy.class)
public interface ConsoleDispatchStrategy extends DispatchStrategy {
    Logger logger = LogManager.getLogger(ConsoleDispatchStrategy.class);

    @JsonProperty("message")
    String getMessage();

    default void dispatch() {
        logger.warn("Alert: `{}`", getMessage());
    }
}
