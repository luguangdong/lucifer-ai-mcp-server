package com.lucifer.ai.mcp.tools;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

/**
 * @author lucifer
 * @date 2025/3/10 16:35
 */
@Service
public class WeatherService {

    @Tool(description = "Get weather information by city name")
    public WeatherResponse getWeather(String cityName, ToolContext context) {
        return new WeatherResponse(168.0, "C");
    }
}


