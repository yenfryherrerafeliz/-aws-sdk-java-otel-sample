package com.herrergy.apps.otel_exporter.db;

import java.util.ArrayList;
import java.util.List;

public class MySQLInsert {
    private final String table;
    private final List<FieldValue> fieldValues;

    public MySQLInsert(String table) {
        this.table = table;
        this.fieldValues = new ArrayList<>();
    }

    public MySQLInsert(String table, List<FieldValue> fieldValues) {
        this.table = table;
        this.fieldValues = fieldValues;
    }

    public void addFieldValue(String field, String value) {
        fieldValues.add(new FieldValue(field, value));
    }

    public void removeField(String field) {
        fieldValues.removeIf(fieldValue -> fieldValue.getField().equals(field));
    }

    public String buildInsertSQL() {
        StringBuilder fieldsBuilder = new StringBuilder();
        StringBuilder valuesBuilder = new StringBuilder();
        String sep = "";

        fieldsBuilder.append("(");
        valuesBuilder.append("(");
        for (FieldValue fieldValue: fieldValues) {
            fieldsBuilder
                    .append(sep)
                    .append(fieldValue.getField());
            valuesBuilder
                    .append(sep)
                    .append("'")
                    .append(fieldValue.getValue())
                    .append("'");

            sep = ",";
        }

        fieldsBuilder.append(")");
        valuesBuilder.append(")");

        return "INSERT INTO " + table + fieldsBuilder + "VALUES" + valuesBuilder;
    }
}
