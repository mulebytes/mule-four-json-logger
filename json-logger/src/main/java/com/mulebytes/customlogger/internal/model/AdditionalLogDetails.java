package com.mulebytes.customlogger.internal.model;

import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;

import java.util.Map;

public class AdditionalLogDetails {
	@Parameter
	@Optional
	@DisplayName("Key-Value Pair")
	private Map<String, String> additionalLogDetails;

	public Map<String, String> getAdditionalLogDetails() {
		return additionalLogDetails;
	}
}
