package com.mulebytes.customlogger.internal.model;

public class ErrorClassification {
    /**
     * Log Trace points
     */
    public enum errorClassification {

        TECHNICAL_ERROR("01"),
        FUNCTIONAL_ERROR("02");

        private final String errorClassification;

        errorClassification(String errorClassification) {
            this.errorClassification = errorClassification;
        }

        public String error_Classification() {
            return errorClassification;
        }
    }
}