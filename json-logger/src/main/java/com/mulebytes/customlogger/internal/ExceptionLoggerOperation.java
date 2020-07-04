package com.mulebytes.customlogger.internal;

import com.mulebytes.customlogger.internal.model.*;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;
import org.json.JSONObject;
import org.mule.runtime.api.component.location.ComponentLocation;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;

import java.time.Instant;
import java.util.HashMap;
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
	 */
	@SuppressWarnings("unused")
	@MediaType(value = ANY, strict = false)
	public void errorLog(
			@ParameterGroup(name = "Exception") ExceptionProperties exceptionProperties,
			ComponentLocation location,
			@Config CustomLoggerConfiguration customLoggerConfiguration) {

		initLogger("com.mule.error.log");

		final Map<LoggerLevelProperty.LogLevel, Level> levelMap = getMappings();

		Map<String, Object> logContent = new HashMap<>();
		logContent.put("appName", customLoggerConfiguration.getAppName());
		logContent.put("appVersion", customLoggerConfiguration.getAppVersion());
		logContent.put("env", customLoggerConfiguration.getEnv());
		logContent.put("timestamp", Instant.now().toString());

		// This is because, we need to see what is in the nested object when the
		// Hashmap is logged.
		Map<String, Object> logOnes = new HashMap<>();
		logOnes.put("transactionId", exceptionProperties.getTransactionId());
		logOnes.put("payload", exceptionProperties.getPayload());
		logOnes.put("tracePoint", exceptionProperties.getTracePoint());
		logContent.put("log", logOnes);

		if (exceptionProperties != null) {
			Map<String, Object> exceptionOnes = new HashMap<>();
			exceptionOnes.put("errorCode", exceptionProperties.getErrorCode());
			exceptionOnes.put("errorType", exceptionProperties.getErrorType());
			exceptionOnes.put("errorClassification", exceptionProperties.getErrorClassification());
			exceptionOnes.put("errorDescription",
					exceptionProperties.getErrorDescription());
			logContent.put("errorDetails", exceptionOnes);
		}

		Map<String, String> locationInfo = new HashMap<>();
		locationInfo.put("location", location.getLocation());
		locationInfo.put("rootContainer", location.getLocation());
		locationInfo.put("component", location.getComponentIdentifier()
				.getIdentifier().toString());
		locationInfo.put("fileName", location.getFileName().orElse(""));

		logContent.put("location", locationInfo);
		ObjectMessage objectMessage = new ObjectMessage(logContent);
		logger.log(levelMap.get(exceptionProperties.getLogLevel()),
				new JSONObject(logContent));
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