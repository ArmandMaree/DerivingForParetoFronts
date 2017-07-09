package com.paretofinder;

public class NodeNotFoundException extends Exception {
	public NodeNotFoundException() {
		super("Node not found in tree.");
	}

	public NodeNotFoundException(String message) {
		super(message);
	}
}