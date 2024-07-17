package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.ClientConfig;
import org.example.service.MonitoringService;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();  // Automatically register all modules, including Immutables

        // Read JSON file into a Map
        Map<String, List<ClientConfig>> configMap = mapper.readValue(new File("src/main/resources/config.json"), new TypeReference<Map<String, List<ClientConfig>>>() {});
        List<ClientConfig> alertConfigList = configMap.get("alertConfigList");

        MonitoringService monitoringService = new MonitoringService(alertConfigList);

        // Simulate events
        for (int i = 0; i < 12; i++) {
            monitoringService.captureEvent("X", "PAYMENT_EXCEPTION");
        }

        for (int i = 0; i < 12; i++) {
            monitoringService.captureEvent("X", "USERSERVICE_EXCEPTION");
        }
    }
}