package com.herrergy.apps;

import com.herrergy.apps.otel_exporter.ConsoleExporter;
import com.herrergy.apps.otel_exporter.MySQLExporter;
import com.herrergy.apps.otel_exporter.db.MappingField;
import com.herrergy.apps.otel_exporter.db.MySQLDbConfig;
import com.herrergy.apps.otel_exporter.db.exception.MissingFieldsException;
import com.herrergy.apps.utils.StringUtils;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;
import io.opentelemetry.sdk.trace.export.SpanExporter;
import io.opentelemetry.sdk.trace.samplers.Sampler;
import io.opentelemetry.semconv.resource.attributes.ResourceAttributes;

import java.util.List;
import java.util.Map;

public class AppSetup {
    private static final String SERVICE_NAME = "aws-java-sdk-otel-sample";
    private static final String INSTANCE_ID = "herrera-instance";

    public static void setupOpenTelemetry() {
        SpanExporter customSpanExporter = getDefaultSpanExporter();
        Resource serviceResource = Resource.create(Attributes.of(ResourceAttributes.SERVICE_NAME, SERVICE_NAME));
        Resource instanceResource = Resource.create(Attributes.of(ResourceAttributes.SERVICE_INSTANCE_ID, INSTANCE_ID));

        OpenTelemetrySdk
                .builder()
                .setTracerProvider(
                        SdkTracerProvider
                                .builder()
                                .setResource(
                                        Resource.getDefault()
                                                .merge(serviceResource)
                                                .merge(instanceResource)
                                )
                                .addSpanProcessor(
                                        SimpleSpanProcessor
                                                .create(customSpanExporter)
                                ).setSampler(
                                        Sampler.alwaysOn()
                                ).build()
                ).buildAndRegisterGlobal();
    }

    private static SpanExporter getDefaultSpanExporter() {
        String exporter = System.getenv("OTEL_EXP_DEFAULT");

        if (StringUtils.isEmpty(exporter)) {
            exporter = "";
        }

        switch (exporter) {
            case "mysql":
                return getMySQLExporter();
            case "console":
            default:
                 return new ConsoleExporter();
        }
    }

    private static MySQLExporter getMySQLExporter() {
        String host = System.getenv("OTEL_EXP_MYSQL_HOST");
        String port = System.getenv("OTEL_EXP_MYSQL_PORT");
        String user = System.getenv("OTEL_EXP_MYSQL_USER");
        String password = System.getenv("OTEL_EXP_MYSQL_PASSWORD");
        String dbName = System.getenv("OTEL_EXP_MYSQL_DB_NAME");

        if (StringUtils.isEmpty(port)) {
            port = "3306";
        }

        MySQLDbConfig config = MySQLDbConfig.builder()
                .host(host)
                .port(port)
                .user(user)
                .password(password)
                .dbName(dbName)
                .build();
        List<MappingField> mappingFields = MappingField.builder()
                .addMapping("traceId", "trace_id")
                .addMapping("spanId", "span_id")
                .addMapping("parentId", "parent_id")
                .addMapping("timeStamp", "timestamp")
                .addMapping("duration", "duration")
                .addMapping("resource/telemetry.sdk.language", "sdk_name")
                .addMapping("resource/service.name", "service_name")
                .addMapping("resource/service.instance.id", "instance_id")
                .addMapping("attributes/rpc.service", "client_name")
                .addMapping("attributes/rpc.method", "action_name")
                .build();

        try {
            return new MySQLExporter(config, mappingFields, true);
        } catch (MissingFieldsException e) {
            throw new RuntimeException(e);
        }
    }
}
