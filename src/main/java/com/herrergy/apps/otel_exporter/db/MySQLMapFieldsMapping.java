package com.herrergy.apps.otel_exporter.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MySQLMapFieldsMapping {
    private final List<MappingField> mappingFields;
    private final Map<String, Object> mapStructure;

    public MySQLMapFieldsMapping(List<MappingField> mappingFields, Map<String, Object> mapStructure) {
        this.mappingFields = mappingFields;
        this.mapStructure = mapStructure;
    }

    public List<FieldValue> runMapping() {
        List<FieldValue> fieldValues = new ArrayList<>();

        for (MappingField mappingField: mappingFields) {
            String sourceField = mappingField.getSource();
            String destinationField = mappingField.getDestination();
            Object sourceValue = getSourceFieldValue(sourceField);

            fieldValues.add(new FieldValue(destinationField, (String) sourceValue));
        }

        return fieldValues;
    }

    private Object getSourceFieldValue(String sourceField) {
        // Example: resource.name will get first resource from the hash map and then name
        Object valueToSearch = mapStructure;
        String[] sourceFieldPaths = sourceField.split("/");

        for (String path: sourceFieldPaths) {
            if (valueToSearch instanceof Map) {
                valueToSearch = ((Map<String, Object>) valueToSearch).get(path);
            } else {
                return null;
            }
        }

        if (valueToSearch == mapStructure) {
            return null;
        }

        return valueToSearch;
    }
}
