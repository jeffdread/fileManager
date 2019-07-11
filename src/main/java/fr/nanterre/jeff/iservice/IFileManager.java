package fr.nanterre.jeff.iservice;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface IFileManager {
	public enum ErrorCode {
		OK("OK"), ERR000("Unknown error"), REG("Regression found");

		private String description = "";

		ErrorCode(String description) {
			this.description = description;
		}

		public String toString() {
			return description;
		}
	}
	
	String convertListStringToString(List<String> stringList);
	Boolean compareFilesContent(InputStream inputStream1, InputStream inputStream2) throws IOException;
}
