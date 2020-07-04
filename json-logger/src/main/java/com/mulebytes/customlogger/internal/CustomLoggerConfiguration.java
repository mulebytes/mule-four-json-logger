package com.mulebytes.customlogger.internal;

import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Summary;

//import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;

/**
 * Custom Logger This class represents an extension configuration, values set in this class are commonly used across multiple
 * operations since they represent something core from the extension.
 */
@Operations({CustomLoggerOperation.class, ExceptionLoggerOperation.class})
public class CustomLoggerConfiguration {

    @Parameter
    @DisplayName("App Name")
    @Summary("Name of the Mule Application")
    @Optional(defaultValue = "#[app.name]")
    private String appName;
    @Parameter
    @DisplayName("App Version")
    @Summary("Version of the Mule Application")
    @Optional(defaultValue = "${appVersion}")
    private String appVersion;
    @Parameter
    @DisplayName("Environment")
    @Summary("Mule Application Environment")
    @Optional(defaultValue = "${env}")
    private String env;
    
    @Parameter
    @DisplayName("Pretty Print JSON Log")
    @Optional(defaultValue = "True")
    public boolean prettyPrint;
    
    public String getAppName() {
        return appName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public String getEnv() {
        return env;
    }
    
    public boolean isPrettyPrint() {
        return prettyPrint;
    }

}