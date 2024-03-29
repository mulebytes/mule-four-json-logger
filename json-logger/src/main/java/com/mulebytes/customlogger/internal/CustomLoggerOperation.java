package com.mulebytes.customlogger.internal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mulebytes.customlogger.internal.model.*;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;
import org.json.*;
import org.mule.runtime.api.component.location.ComponentLocation;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;

import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

/**
 * Author: Jagadishwar Reddy
 */
@SuppressWarnings("unused")
public class CustomLoggerOperation {

	private Logger logger;

	@MediaType(value = ANY, strict = false)
	public void log(@ParameterGroup(name = "Log") LogProperties logProperties,
			@ParameterGroup(name = "Optional Details") OptionalProperties optionalProperties,
			@ParameterGroup(name = "Additional Log Info") ExtendedPoperties extendedLogInfo,
			ComponentLocation location,
			@Config CustomLoggerConfiguration customLoggerConfiguration) throws JsonProcessingException {

		if (logProperties.getCategory() != null)
			initLogger(logProperties.getCategory());
		else
			initLogger("com.mule.log");

		final Map<LoggerLevelProperty.LogLevel, Level> levelMap = getMappings();

		LinkedHashMap<String, Object> logContent = new LinkedHashMap<>();
		logContent.put("timestamp", logProperties.getTimestamp());		
		logContent.put("applicationName", customLoggerConfiguration.getAppName());
		logContent.put("applicationVersion", customLoggerConfiguration.getAppVersion());
		logContent.put("environment", customLoggerConfiguration.getEnv());
		
		if(optionalProperties != null){
			logContent.put("businessProcessName", optionalProperties.getBusinessProcessName());
			logContent.put("sourceSystem", optionalProperties.getSourceSystem());
			logContent.put("destinationSystem", optionalProperties.getDestinationSystem());
		}
		
		LinkedHashMap<String, Object> logOnes = new LinkedHashMap<>();
		logOnes.put("transactionId", logProperties.getTransactionId());
		logOnes.put("correlationId", logProperties.getCorrelationId());
		logOnes.put("tracePoint", logProperties.getTracePoint());
		logOnes.put("logMessage", logProperties.getMessage());
		logOnes.put("payload", logProperties.getPayload());
		logContent.put("log", logOnes);

		LinkedHashMap<String, String> locationInfo = new LinkedHashMap<>();		
		locationInfo.put("component", location.getComponentIdentifier()
				.getIdentifier().toString());
		locationInfo.put("fileName", location.getFileName().orElse(""));
		locationInfo.put("lineNumber", location.getLineInFile().toString());
		locationInfo.put("rootContainer", location.getRootContainerName());
		logContent.put("logLocation", locationInfo);
		
		if(extendedLogInfo.getExtendedPoperties() != null)
		logContent.put("additionalLogDetails", extendedLogInfo.getExtendedPoperties());
		ObjectMapper mapper = new ObjectMapper();
		String finalLog= (customLoggerConfiguration.isPrettyPrint())? mapper.writerWithDefaultPrettyPrinter().writeValueAsString(logContent): mapper.writeValueAsString(logContent);
		logger.log(levelMap.get(logProperties.getLogLevel()), finalLog);
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