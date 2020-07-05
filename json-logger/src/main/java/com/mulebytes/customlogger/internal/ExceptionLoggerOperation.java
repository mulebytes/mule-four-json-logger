package com.mulebytes.customlogger.internal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulebytes.customlogger.internal.model.*;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mule.runtime.api.component.location.ComponentLocation;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

/**
 * Author: Jagadishwar Reddy
 */
public class ExceptionLoggerOperation {

	private Logger logger;

	/**
	 * Author: Jagadishwar Reddy
	 *
	 * @return
	 * @throws JsonProcessingException 
	 */
	@MediaType(value = ANY, strict = false)
	public void errorLog(@ParameterGroup(name = "Exception") ExceptionProperties exceptionProperties,
			@ParameterGroup(name = "Optional Details") OptionalProperties optionalProperties,
			@ParameterGroup(name = "Additional Error Info") ExtendedPoperties extendedErrorInfo,
			ComponentLocation location,
			@Config CustomLoggerConfiguration customLoggerConfiguration) throws JsonProcessingException {

		initLogger("com.mule.error.log");

		final Map<LoggerLevelProperty.LogLevel, Level> levelMap = getMappings();

		LinkedHashMap<String, Object> logContent = new LinkedHashMap<>();
		logContent.put("timestamp", exceptionProperties.getTimestamp());
		logContent.put("applicationName", customLoggerConfiguration.getAppName());
		logContent.put("applicationVersion", customLoggerConfiguration.getAppVersion());
		logContent.put("environment", customLoggerConfiguration.getEnv());

		if(optionalProperties != null){
			logContent.put("businessProcessName", optionalProperties.getBusinessProcessName());
			logContent.put("sourceSystem", optionalProperties.getSourceSystem());
			logContent.put("destinationSystem", optionalProperties.getDestinationSystem());
		}
		LinkedHashMap<String, Object> logOnes = new LinkedHashMap<>();
		logOnes.put("transactionId", exceptionProperties.getTransactionId());
		logOnes.put("correlationId", exceptionProperties.getCorrelationId());
		logOnes.put("payload", exceptionProperties.getPayload());
		logOnes.put("tracePoint", exceptionProperties.getTracePoint());
		logContent.put("log", logOnes);

		if (exceptionProperties != null) {
			LinkedHashMap<String, Object> exceptionOnes = new LinkedHashMap<>();
			exceptionOnes.put("errorCode", exceptionProperties.getErrorCode());
			exceptionOnes.put("errorType", exceptionProperties.getErrorType());
			exceptionOnes.put("errorSeverity", exceptionProperties.getErrorSeverity());
			exceptionOnes.put("errorClassification", exceptionProperties.getErrorClassification());
			exceptionOnes.put("errorDescription",exceptionProperties.getErrorDescription());
			logContent.put("errorDetails", exceptionOnes);
		}

		LinkedHashMap<String, String> locationInfo = new LinkedHashMap<>();		
		locationInfo.put("component", location.getComponentIdentifier()
				.getIdentifier().toString());
		locationInfo.put("fileName", location.getFileName().orElse(""));
		locationInfo.put("lineNumber", location.getLineInFile().toString());
		locationInfo.put("rootContainer", location.getRootContainerName());
		logContent.put("logLocation", locationInfo);
		
		if(extendedErrorInfo.getExtendedPoperties() != null)
		logContent.put("additionalErrorDetails", extendedErrorInfo.getExtendedPoperties());
		ObjectMapper mapper = new ObjectMapper();
		String finalLog= (customLoggerConfiguration.isPrettyPrint())? mapper.writerWithDefaultPrettyPrinter().writeValueAsString(logContent): mapper.writeValueAsString(logContent);
		logger.log(levelMap.get(exceptionProperties.getLogLevel()), finalLog);
	}

	private void initLogger(String category) {
		this.logger = LogManager.getLogger(category);
	}

	private static Map<LoggerLevelProperty.LogLevel, Level> getMappings() {
		Map<LoggerLevelProperty.LogLevel, Level> map = new HashMap<>();
		map.put(LoggerLevelProperty.LogLevel.INFO, Level.INFO);
		map.put(LoggerLevelProperty.LogLevel.DEBUG, Level.DEBUG);
		map.put(LoggerLevelProperty.LogLevel.TRACE, Level.TRACE);
		map.put(LoggerLevelProperty.LogLevel.ERROR, Level.ERROR);
		map.put(LoggerLevelProperty.LogLevel.WARN, Level.WARN);
		map.put(LoggerLevelProperty.LogLevel.FATAL, Level.FATAL);
		return map;
	}
}