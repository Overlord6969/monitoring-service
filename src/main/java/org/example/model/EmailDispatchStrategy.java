package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableEmailDispatchStrategy.class)
public interface EmailDispatchStrategy extends DispatchStrategy {
    Logger logger = LogManager.getLogger(EmailDispatchStrategy.class);

    @JsonProperty("subject")
    String getSubject();

    default void dispatch() {
        logger.info("Sending Email: `{}`", getSubject());
    }
}
