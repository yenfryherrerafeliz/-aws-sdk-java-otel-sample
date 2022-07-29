package com.herrergy.apps.otel_exporter.db;

import com.herrergy.apps.otel_exporter.db.exception.MissingFieldsException;
import com.herrergy.apps.utils.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class MySQLDbConfig {
    private String host;
    private String port;
    private String user;
    private String password;
    private String dbName;

    private MySQLDbConfig(String host, String port, String user, String password, String dbName) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.dbName = dbName;
    }

    public static Builder builder() {
        return new Builder();
    }

    private String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    private String getUser() {
        return user;
    }

    public void setPort(String port) {
        this.port = port;
    }

    private String getPort() {
        return this.port;
    }

    public void setUser(String user) {
        this.user = user;
    }

    private String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String buildConnectionURL() {
        return "jdbc:mysql://" + host + ":" +  port + "/" + dbName + "?user=" + user + "&password=" + password;
    }

    public void validateFields() throws MissingFieldsException {
        StringBuilder missingFields = new StringBuilder();
        Field[] fields = getClass().getDeclaredFields();
        String sep="";

        for (Field field: fields) {
            String getMethodName = "get" + StringUtils.capitalize(field.getName());
            try {
                Method method = getClass().getDeclaredMethod(getMethodName);
                String value = (String) method.invoke(this);
                if (StringUtils.isEmpty(value)) {
                    missingFields.append(sep).append(field.getName());
                    sep = ",";
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {

            }
        }

        if (!StringUtils.isEmpty(missingFields.toString())) {
            throw new MissingFieldsException("The following fields (" + missingFields + ") can not be empty!");
        }
    }

    public static class Builder {
        private String host;
        private String port;
        private String user;
        private String password;
        private String dbName;

        public Builder() {

        }

        public Builder host(String host) {
            this.host = host;
            return this;
        }

        public Builder port(String port) {
            this.port = port;
            return this;
        }

        public Builder user(String user) {
            this.user = user;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder dbName(String dbName) {
            this.dbName = dbName;
            return this;
        }

        public MySQLDbConfig build() {
            return new MySQLDbConfig(host, port, user, password, dbName);
        }
    }
}