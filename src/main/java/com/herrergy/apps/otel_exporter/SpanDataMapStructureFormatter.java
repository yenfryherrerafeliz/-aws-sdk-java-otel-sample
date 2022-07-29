package com.herrergy.apps.otel_exporter;

import com.herrergy.apps.utils.StringUtils;

import java.util.Map;
import java.util.Set;

public class SpanDataMapStructureFormatter {
    private static final String TAB = "  ";
    private static final String NEW_LINE = "\n";

    private Map<String, Object> rootSpanDataStructure;

    public SpanDataMapStructureFormatter(Map<String, Object> rootSpanDataStructure) {
        this.rootSpanDataStructure = rootSpanDataStructure;
    }

    public String format() {
        return this.format(this.rootSpanDataStructure, 1);
    }

    private String format(Map<String, Object> mapStructure, int level) {
        StringBuilder builder = new StringBuilder();
        Set<Map.Entry<String, Object>> structureEntries = mapStructure.entrySet();

        builder.append(StringUtils.repeat(TAB, level == 1 ? 0 : 1))
                .append("{")
                .append(StringUtils.repeat(TAB, level));

        for (Map.Entry<String, Object> entry: structureEntries) {
            Object value = entry.getValue();

            builder.append(NEW_LINE)
                    .append(StringUtils.repeat(TAB, level))
                    .append(entry.getKey())
                    .append(":")
                    .append(" ");

            if (value instanceof Map) {
                builder.append(this.format((Map<String, Object>) value, level + 1));
                // builder.append(StringUtils.repeat(TAB, level));
            } else {
                builder.append("'")
                        .append(value)
                        .append("'")
                        .append(",");
            }
        }

        builder.append(NEW_LINE).
                append(StringUtils.repeat(TAB, level - 1))
                .append("},");

        return builder.toString();
    }
}
