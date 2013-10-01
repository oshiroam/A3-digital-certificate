package br.com.javac.nfeapplet.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Utils {

	public static String getUserHome() {
		return System.getProperty("user.home");
	}
	
	public static String lerXML(String fileXML) throws IOException {
		String linha = "";
		StringBuilder xml = new StringBuilder();

		BufferedReader in = new BufferedReader(new InputStreamReader(
				new FileInputStream(fileXML)));
		while ((linha = in.readLine()) != null) {
			xml.append(linha);
		}
		in.close();

		return xml.toString();
	}

	/**
	 * Retira quebras de linhas do XML e tag desnecessaria
	 * na declaração do XML.
	 * @param xml
	 * @return
	 */
	public static String normalizeXML(String xml) {
		if ((xml != null) && (!"".equals(xml))) {
			xml = xml.replaceAll("\\r\\n", "");
			xml = xml.replaceAll(" standalone=\"no\"", "");
		}
		return xml;
	}
	
}
