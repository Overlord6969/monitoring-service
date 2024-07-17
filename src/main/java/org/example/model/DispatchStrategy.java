package org.example.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ImmutableConsoleDispatchStrategy.class, name = "CONSOLE"),
        @JsonSubTypes.Type(value = ImmutableEmailDispatchStrategy.class, name = "EMAIL")
})
public interface DispatchStrategy {

    @JsonProperty("type")
    String getType();

    void dispatch();
}

