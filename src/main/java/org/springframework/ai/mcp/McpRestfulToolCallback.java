package org.springframework.ai.mcp;

import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.definition.ToolDefinition;
import org.springframework.ai.util.json.JsonParser;
import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lucifer
 * @date 2025/8/8 13:39
 */
public class McpRestfulToolCallback implements ToolCallback {
    private static final Logger logger = LoggerFactory.getLogger(McpRestfulToolCallback.class);

    private final RestfulToolDefinition toolDefinition;
    private Map<String, String> headers = new HashMap<>();

    public McpRestfulToolCallback(RestfulToolDefinition toolDefinition) {
        Assert.notNull(toolDefinition, "toolDefinition cannot be null");
        this.toolDefinition = toolDefinition;
    }

    @Override
    public ToolDefinition getToolDefinition() {
        return toolDefinition;
    }

    @Override
    public String call(String toolInput) {
        return this.call(toolInput, (ToolContext) null);
    }

    @Override
    public String call(String toolInput, @Nullable ToolContext toolContext) {
        Assert.hasText(toolInput, "toolInput cannot be null or empty");
        logger.debug("Starting execution of tool: {}", this.toolDefinition.name());

        Map<String, Object> toolArguments = JsonParser.fromJson(toolInput, new TypeReference<Map<String, Object>>() {
        });
        String result = "";
        if (HttpMethod.GET.equals(toolDefinition.httpMethod())) {
            StringBuilder uriBuilder = new StringBuilder().append(toolDefinition.path()).append("?");
            toolArguments.forEach((key, value) -> {
                uriBuilder.append(key).append("=").append(value).append("&");
            });
            String uri = uriBuilder.toString();

            result = WebClient.builder().build().get()
                    .uri(toolDefinition.url() + uri)
                    .headers(headers -> {
                        this.headers.forEach(headers::add);
                    })
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } else if (HttpMethod.POST.equals(toolDefinition.httpMethod())) {
            result = WebClient.builder().build().post()
                    .uri(toolDefinition.url())
                    .headers(headers -> {
                        this.headers.forEach(headers::add);
                    })
                    .bodyValue(toolArguments)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

        }
        logger.debug("Successful execution of tool: {}, result: {}", this.toolDefinition.name(), result);
        return result;
    }

    public void setHeaders(Map<String, String> headersMap) {
        this.headers = headersMap;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private RestfulToolDefinition toolDefinition;

        private Builder() {
        }

        public Builder toolDefinition(RestfulToolDefinition toolDefinition) {
            this.toolDefinition = toolDefinition;
            return this;
        }


        public McpRestfulToolCallback build() {
            return new McpRestfulToolCallback(this.toolDefinition);
        }

    }
}
