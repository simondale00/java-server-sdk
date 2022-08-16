package com.eppo.sdk.dto;

import java.util.Map;

/**
 * Experiment Configuration Response Class
 */
public class ExperimentConfigurationResponse {
    public Map<String, ExperimentConfiguration> experiments;

    @Override
    public String toString() {
       return "[Experiments: " + experiments.toString() + "]";
    }
}