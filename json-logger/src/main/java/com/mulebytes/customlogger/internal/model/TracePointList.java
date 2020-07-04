package com.mulebytes.customlogger.internal.model;

public class TracePointList {
    /**
     * Log Trace points
     */
    public enum tracePoint {

        START("01"),
        END("02"),
        BEFORE_CONNECTOR("03"),
        AFTER_CONNECTOR("04"),
        BEFORE_TRANSFORMATION("05"),
        AFTER_TRANSFORMATION("06"),
        FLOW("07"),
        EXCEPTION("08");

        private final String tracePoint;

        tracePoint(String tracePoint) {
            this.tracePoint = tracePoint;
        }

        public String trace_point() {
            return tracePoint;
        }
    }
}