package com.herrergy.apps.otel_exporter;

import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.sdk.trace.data.EventData;
import io.opentelemetry.sdk.trace.data.LinkData;
import io.opentelemetry.sdk.trace.data.SpanData;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BaseExporter {

    protected Map<String, Object> buildMapStructureForSpanData(SpanData spanData) {
        Long duration = (spanData.getEndEpochNanos() - spanData.getStartEpochNanos()) / 1_000_000;

        Map<String, Object> spanDataMap = new HashMap<>();
        spanDataMap.put("traceId", spanData.getTraceId());
        spanDataMap.put("spanId", spanData.getSpanId());
        spanDataMap.put("name", spanData.getName());
        spanDataMap.put("parentId", spanData.getParentSpanId());
        spanDataMap.put("kind", spanData.getKind());
        spanDataMap.put("timeStamp", String.valueOf(LocalDateTime.now()));
        spanDataMap.put("duration", String.valueOf(duration));
        spanDataMap.put("durationUnit", "milliseconds");
        spanDataMap.put("attributes", buildMapStructureForAttributes(spanData.getAttributes()));
        spanDataMap.put("resource", buildMapStructureForAttributes(spanData.getResource().getAttributes()));
        spanDataMap.put("events", buildMapStructureForEvents(spanData.getEvents()));
        spanDataMap.put("links", buildMapStructureForLinks(spanData.getLinks()));

        return spanDataMap;
    }

    private Map<String, Object> buildMapStructureForAttributes(Attributes attributes) {
        Map<String, Object> attributesMap = new HashMap<>();

        attributes.forEach((key, value) -> {
            attributesMap.put(String.valueOf(key), value);
        });

        return attributesMap;
    }

    private Map<String, Object> buildMapStructureForEvents(List<EventData> events) {
        Map<String, Object> eventMap = new HashMap<>();

        for (EventData eventData: events) {
            eventMap.put(eventData.getName(), buildMapStructureForAttributes(eventData.getAttributes()));
        }

        return eventMap;
    }

    private Map<String, Object> buildMapStructureForLinks(List<LinkData> links) {
        Map<String, Object> linkMap = new HashMap<>();
        int linkCount = 1;

        for (LinkData linkData: links) {
            linkMap.put(String.valueOf(linkCount), buildMapStructureForAttributes(linkData.getAttributes()));
            linkCount++;
        }

        return linkMap;
    }

    protected String formatMapStructure(Map<String, Object> structure) {
        return new SpanDataMapStructureFormatter(structure).format();
    }
}
