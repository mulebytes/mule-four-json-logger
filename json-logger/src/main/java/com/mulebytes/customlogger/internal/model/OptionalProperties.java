package com.mulebytes.customlogger.internal.model;

import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Summary;

public class OptionalProperties {
	@Parameter
	@DisplayName("Business Process Name")
	@Summary("Business Process Name")
	@Optional(defaultValue = "Employee Sync Up")
	private String businessProcessName;
	
	@Parameter
	@DisplayName("Source System")
	@Summary("Source System Name")
	@Optional(defaultValue = "HTTP Web or Mobile Client")
	private String sourceSystem;
	
	@Parameter
	@DisplayName("Destination System")
	@Summary("Destination System Name")
	@Optional(defaultValue = "Database")
	private String destinationSystem;

	public String getBusinessProcessName() {
		return businessProcessName;
	}

	public String getSourceSystem() {
		return sourceSystem;
	}

	public String getDestinationSystem() {
		return destinationSystem;
	}
	
}
