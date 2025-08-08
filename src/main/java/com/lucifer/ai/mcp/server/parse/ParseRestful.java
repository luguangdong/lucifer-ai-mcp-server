package com.lucifer.ai.mcp.server.parse;
import com.lucifer.ai.mcp.server.model.Parameter;
import com.lucifer.ai.mcp.server.model.RestfulModel;
import com.lucifer.ai.mcp.server.util.JSONSchemaUtil;
import org.springframework.ai.mcp.McpRestfulToolCallback;
import org.springframework.ai.mcp.McpRestfulToolCallbackProvider;
import org.springframework.ai.mcp.RestfulToolDefinition;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lucifer
 * @date 2025/8/8 13:40
 */
@Component
public class ParseRestful {

    public McpRestfulToolCallbackProvider getRestfulToolCallbackProvider() {
        List<McpRestfulToolCallback> toolCallbacks = new ArrayList<>();
        getRestfulModels().forEach(
                restfulModel -> {
                    RestfulToolDefinition restfulToolDefinition = RestfulToolDefinition.builder()
                            .name(restfulModel.name())
                            .description(restfulModel.description())
                            .inputSchema(restfulModel.inputSchema())
                            .url(restfulModel.url())
                            .method(restfulModel.method())
                            .path(restfulModel.path())
                            .httpMethod(restfulModel.httpMethod())
                            .build();
                    McpRestfulToolCallback mcpRestfulToolCallback = McpRestfulToolCallback.builder().toolDefinition(restfulToolDefinition).build();

                    toolCallbacks.add(mcpRestfulToolCallback);
                });
        return McpRestfulToolCallbackProvider.builder()
                .toolCallbacks(toolCallbacks.toArray(new McpRestfulToolCallback[0]))
                .build();
    }


    public List<RestfulModel> getRestfulModels() {

        Parameter parameter = Parameter.builder()
                .parameteNname("timeZoneId")
                .description("time zone id, such as Asia/Shanghai")
                .required(true)
                .type("string")
                .build();
        return List.of(
                new RestfulModel("getCityTime", "获取指定时区的时间", JSONSchemaUtil.getInputSchema(List.of(parameter)), "http://localhost:8001", "getCiteTimeMethod", "/time/city", HttpMethod.GET)
        );
    }

}
