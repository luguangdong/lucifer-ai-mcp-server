package com.lucifer.ai.mcp;
import com.lucifer.ai.mcp.server.parse.ParseRestful;
import com.lucifer.ai.mcp.tools.WeatherService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class LuciferAiMcpServer {
	public static void main(String[] args) {
		SpringApplication.run(LuciferAiMcpServer.class, args);
	}

	@Bean
	public ToolCallbackProvider weatherTools(WeatherService weatherService) {
		return MethodToolCallbackProvider.builder().toolObjects(weatherService).build();
	}

	@Bean
	public ToolCallbackProvider mcpRestfulToolCallbackProvider(ParseRestful parseRestful) {
		return parseRestful.getRestfulToolCallbackProvider();
	}
}
