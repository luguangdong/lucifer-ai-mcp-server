package com.lucifer.ai.mcp.server.model;

import org.springframework.http.HttpMethod;

/**
 * @author lucifer
 * @date 2025/8/8 13:40
 */

public record RestfulModel(String name, String description, String inputSchema, String url, String method, String path, HttpMethod httpMethod) {

}
