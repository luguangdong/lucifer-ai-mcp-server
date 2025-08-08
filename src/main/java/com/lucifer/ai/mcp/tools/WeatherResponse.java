package com.lucifer.ai.mcp.tools;

import lombok.Data;

/**
 * @author lucifer
 * @date 2025/3/10 16:38
 */
@Data
public class WeatherResponse {

    private double temp;

    private String unit;

    public WeatherResponse(double temp, String unit) {
        this.temp = temp;
        this.unit = unit;
    }
}
