package com.obs.dqsc.api.exception.functional;


/**
 * @author  Othman CHAHBOUNE
 * @since 11-11-2021
 */
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String resources) {
        super("No " + resources +" were found");
    }
}
