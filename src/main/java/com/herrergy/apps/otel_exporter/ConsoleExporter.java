package com.herrergy.apps.otel_exporter;

import io.opentelemetry.sdk.common.CompletableResultCode;
import io.opentelemetry.sdk.trace.data.SpanData;
import io.opentelemetry.sdk.trace.export.SpanExporter;

import java.util.Collection;
import java.util.Map;

public class ConsoleExporter extends BaseExporter implements SpanExporter {

    @Override
    public CompletableResultCode export(Collection<SpanData> spans) {

        for (SpanData spanData: spans) {
            Map<String, Object> structure = buildMapStructureForSpanData(spanData);
            String formatted = formatMapStructure(structure);

            System.out.println(formatted);
        }
        return CompletableResultCode.ofSuccess();
    }

    @Override
    public CompletableResultCode flush() {
        return CompletableResultCode.ofSuccess();
    }

    @Override
    public CompletableResultCode shutdown() {
        return CompletableResultCode.ofSuccess();
    }
}
