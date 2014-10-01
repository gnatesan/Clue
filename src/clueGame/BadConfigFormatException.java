package clueGame;

public class BadConfigFormatException extends RuntimeException {
	public BadConfigFormatException(String message) {
		super(message);
	}
	public BadConfigFormatException() {
		super("File configuration is not correct.");
	}
	public String toString() {
		return "Error: File configuration is not correct";
	}
}