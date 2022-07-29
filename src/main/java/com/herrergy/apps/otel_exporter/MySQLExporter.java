package com.herrergy.apps.otel_exporter;

import com.herrergy.apps.otel_exporter.db.*;
import com.herrergy.apps.otel_exporter.db.exception.MissingFieldsException;
import io.opentelemetry.sdk.common.CompletableResultCode;
import io.opentelemetry.sdk.trace.data.SpanData;
import io.opentelemetry.sdk.trace.export.SpanExporter;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class MySQLExporter extends BaseExporter implements SpanExporter {

    // Should be configurable
    private static final String TRACES_TABLE_NAME = "otel_traces";

    private final List<MappingField> mappings;

    private final boolean printOutput;
    private final MySQLDbConfig dbConfig;

    private MySQLOperator mySQLOperator;

    public MySQLExporter(MySQLDbConfig dbConfig, List<MappingField> mappings, boolean printOutput) throws MissingFieldsException {
        this.mappings = mappings;
        this.printOutput = printOutput;
        this.dbConfig = dbConfig;

        this.dbConfig.validateFields();
    }

    @Override
    public CompletableResultCode export(Collection<SpanData> spans) {
        this.connectIfNotConnected();

        for (SpanData spanData: spans) {
            Map<String, Object> structure = buildMapStructureForSpanData(spanData);

            if (printOutput) {
                String formatted = formatMapStructure(structure);
                System.out.println(formatted);
            }

            this.exportSpanDataToMySQL(structure);
        }

        this.closeMySQLConnection();

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

    private void connectIfNotConnected() {
        try {
            mySQLOperator = new MySQLOperator(this.dbConfig);
            mySQLOperator.connect();
        } catch (MissingFieldsException | SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void closeMySQLConnection() {
        try {
            mySQLOperator.close();
        } catch (SQLException ignored) {

        }
    }

    private void exportSpanDataToMySQL(Map<String, Object> structure) {
        try {
            List<FieldValue> fieldValues = new MySQLMapFieldsMapping(this.mappings, structure).runMapping();
            MySQLInsert insert = new MySQLInsert(TRACES_TABLE_NAME, fieldValues);
            mySQLOperator.insert(insert);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
