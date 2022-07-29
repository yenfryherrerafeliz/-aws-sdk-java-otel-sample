package com.herrergy.apps.otel_exporter.db;

import com.herrergy.apps.otel_exporter.db.exception.MissingFieldsException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLOperator {

    private final MySQLDbConfig dbConfig;
    private Connection connection;
    private Statement statement;

    public MySQLOperator(MySQLDbConfig dbConfig) throws MissingFieldsException {
        this.dbConfig = dbConfig;
        this.dbConfig.validateFields();
    }

    public void connect() throws SQLException, ClassNotFoundException {
        this.close();
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.connection = DriverManager.getConnection(dbConfig.buildConnectionURL());
        this.statement = this.connection.createStatement();
    }

    public int insert(MySQLInsert insert) throws SQLException {
        this.statement.execute(insert.buildInsertSQL());
        return this.statement.getUpdateCount();
    }

    public void close() throws SQLException {
        if (this.connection != null) {
            this.connection.close();
        }
    }

    public boolean isConnected() {
        return this.connection != null;
    }

}
