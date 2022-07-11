package edu.alkemy.challenge.exception;

public class ParamNotFoundException extends RuntimeException {
    public ParamNotFoundException(String msg) {
        super(msg);
    }
}
