package com.paretofinder;

public class CannotReduceException extends Exception {
	public CannotReduceException() {
		super("Subtree contains variable node.");
	}

	public CannotReduceException(String message) {
		super(message);
	}
}