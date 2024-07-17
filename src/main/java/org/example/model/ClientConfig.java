package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@JsonDeserialize(as = ImmutableClientConfig.class)
public interface ClientConfig {

    @JsonProperty("client")
    String getClient();

    @JsonProperty("eventType")
    String getEventType();

    @JsonProperty("alertConfig")
    AlertConfig getAlertConfig();

    @JsonProperty("dispatchStrategyList")
    List<DispatchStrategy> getDispatchStrategyList();
}
