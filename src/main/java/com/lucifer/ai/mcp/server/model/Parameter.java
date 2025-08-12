package com.lucifer.ai.mcp.server.model;

/**
 * @author lucifer
 * @date 2025/8/8 13:40
 */
public record Parameter(String parameterName,
                        String description,
                        boolean required,
                        String type
) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String parameterName;
        private String description;
        private boolean required;
        private String type;

        public Builder parameterName(String parameterName) {
            this.parameterName = parameterName;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder required(boolean required) {
            this.required = required;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Parameter build() {
            return new Parameter(parameterName, description, required, type);
        }

    }

}
