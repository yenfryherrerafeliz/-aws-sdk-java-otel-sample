package com.herrergy.apps.otel_exporter.db;

import java.util.ArrayList;
import java.util.List;

public class FieldValue {
    private String field;
    private String value;

    public static Builder builder() {
        return new Builder();
    }

    public FieldValue(String field, String value) {
        this.field = field;
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static class Builder {
        private final List<FieldValue> fieldValues;

        public Builder() {
            this.fieldValues = new ArrayList<>();
        }

        public Builder addFieldValue(String field, String value) {
            this.fieldValues.add(new FieldValue(field, value));

            return this;
        }

        public List<FieldValue> build() {
            return this.fieldValues;
        }
    }
}
