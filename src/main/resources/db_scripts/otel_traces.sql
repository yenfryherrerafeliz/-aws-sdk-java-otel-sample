CREATE TABLE `otel_traces`
(`trace_id` varchar(36) DEFAULT NULL,
`span_id` varchar(36) DEFAULT NULL,
`parent_id` varchar(36) DEFAULT NULL,
`sdk_name` varchar(64) DEFAULT NULL,
`service_name` varchar(64) DEFAULT NULL,
`instance_id` varchar(128) DEFAULT NULL,
`client_name` varchar(128) DEFAULT NULL,
`action_name` varchar(128) DEFAULT NULL,
`duration` bigint DEFAULT NULL,
`timestamp` datetime DEFAULT NULL);
