package com.mulebytes.customlogger.internal.model;

import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;

import java.util.Map;

public class ExtendedPoperties {
	@Parameter
	@Optional
	@DisplayName("Key-Value Pair")
	private Map<String, String> extendedProperties;

	public Map<String, String> getExtendedPoperties() {
		return extendedProperties;
	}
}
