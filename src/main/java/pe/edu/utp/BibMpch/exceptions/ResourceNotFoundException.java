package pe.edu.utp.BibMpch.exceptions;

import lombok.ToString;

@ToString
public class ResourceNotFoundException extends Exception {
	public ResourceNotFoundException(String message) {
		super(message);
	}
}
