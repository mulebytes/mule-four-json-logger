package com.mulebytes.customlogger.internal.model;

import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Example;
import org.mule.runtime.extension.api.annotation.param.display.Summary;

public class ExceptionProperties {

	@Parameter
	@DisplayName("Error Classification")
	@Optional(defaultValue = "TECHNICAL_ERROR")
	private ErrorClassification.errorClassification errorClassification;
	@Parameter
	@Optional(defaultValue = "#[now()]")
	@DisplayName("Timestamp")
	@Summary("Timestamp")
	private String timeStamp;
	@Parameter
	@DisplayName("Error Code")
	@Summary("Error Code")
	@Optional(defaultValue = "ERR001")
	private String errorCode;
	@Parameter
	@DisplayName("Error Type")
	@Summary("Type of Exception")
	@Optional(defaultValue = "#[[error.errorType.namespace default (app.name),error.errorType.identifier default \"ERROR\"] joinBy \":\"]")
	private String errorType;
	@Parameter
	@DisplayName("Description")
	@Summary("Exception Description")
	@Optional(defaultValue = "#[error.description default \"Error Occured\" replace \"\\\"\" with \"\"]")
	private String errorDescription;

	@Parameter
	@Optional(defaultValue = "#[uuid()]")
	@DisplayName("Transaction ID")
	@Summary("Transaction UUID")
	@Example("#[vars.transactionId]")
	private String transactionId;

	@Parameter
	@Optional(defaultValue = "#[if((isEmpty(payload)) != true and payload != null)write(payload, \"$(payload.^mimeType)\") else null]")
	@DisplayName("Payload")
	@Summary("Payload to be logged")
	private String payload;

	@Parameter
	@DisplayName("Level")
	@Optional(defaultValue = "ERROR")
	private LoggerLevelProperty.LogLevel level;

	@Parameter
	@DisplayName("Trace Point")
	@Optional(defaultValue = "EXCEPTION")
	private TracePointList.tracePoint tracePoint;

	@Parameter
	@Optional(defaultValue = "com.mule.error.log")
	@DisplayName("Category")
	private String category;

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

	public ErrorClassification.errorClassification getErrorClassification() {
		return errorClassification;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorType() {
		return errorType;
	}

	public String getErrorDescription() {
		return errorDescription;
	}
}
