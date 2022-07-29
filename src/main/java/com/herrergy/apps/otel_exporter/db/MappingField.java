package com.herrergy.apps.otel_exporter.db;

import java.util.ArrayList;
import java.util.List;

public class MappingField {
    private String source;
    private String destination;

    public MappingField(String source, String destination) {
        this.source = source;
        this.destination = destination;
    }

    public static Builder builder() {
        return new Builder();
    }
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public static class Builder {
        private final List<MappingField> mappingFields;

        public Builder() {
            this.mappingFields = new ArrayList<>();
        }

        public Builder addMapping(String source, String destination) {
            this.mappingFields.add(new MappingField(source, destination));

            return this;
        }

        public List<MappingField> build() {
            return this.mappingFields;
        }
    }
}
