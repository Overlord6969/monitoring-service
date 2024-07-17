package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableAlertConfig.class)
public interface AlertConfig {
    enum Type {
        SIMPLE_COUNT, TUMBLING_WINDOW, SLIDING_WINDOW
    }

    @JsonProperty("type")
    Type getType();

    @JsonProperty("count")
    int getCount();

    @JsonProperty("windowSizeInSecs")
    int getWindowSizeInSecs();
}

