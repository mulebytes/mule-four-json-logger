package com.mulebytes.customlogger.internal.model;

import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Example;
import org.mule.runtime.extension.api.annotation.param.display.Summary;

public class LogProperties {

	@Parameter
	@Optional(defaultValue = "#[uuid()]")
	@DisplayName("Transaction ID")
	@Summary("Transaction UUID")
	@Example("#[vars.transactionId]")
	private String transactionId;
	@Parameter
	@Optional(defaultValue = "#[now()]")
	@DisplayName("Timestamp")
	@Summary("Timestamp")
	private String timeStamp;
	@Parameter
	@Optional
	@DisplayName("Message")
	@Summary("Message to be logged")
	private String message;

	@Parameter
	@Optional(defaultValue = "#[if((isEmpty(payload)) != true and payload != null)write(payload, \"$(payload.^mimeType)\") else null]")
	@DisplayName("Payload")
	@Summary("Payload to be logged")
	private String payload;

	@Parameter
	@DisplayName("Level")
	@Optional(defaultValue = "INFO")
	private LoggerLevelProperty.LogLevel level;

	@Parameter
	@DisplayName("Trace Point")
	@Optional(defaultValue = "FLOW")
	private TracePointList.tracePoint tracePoint;

	@Parameter
	@Optional(defaultValue = "com.mule.log")
	@DisplayName("Category")
	private String category;

	public String getMessage() {
		return message;
	}

	public LoggerLevelProperty.LogLevel getLogLevel() {
		return level;
	}

	public String getCategory() {
		return category;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public String getTimestamp() {
		return timeStamp;
	}

	public String getPayload() {
		return payload;
	}

	public TracePointList.tracePoint getTracePoint() {
		return tracePoint;
	}

}