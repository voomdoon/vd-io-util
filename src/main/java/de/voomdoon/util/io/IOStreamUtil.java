package de.voomdoon.util.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;

import lombok.experimental.UtilityClass;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since DOCME add inception version number
 */
@UtilityClass
public class IOStreamUtil {

	/**
	 * Copies all data from {@link InputStream} to {@link OutputStream} and closes both streams.
	 * 
	 * @param inputStream
	 *            {@link InputStream}
	 * @param outputStream
	 *            {@link OutputStream}
	 * @throws IOException
	 * @since DOCME add inception version number
	 */
	public static void copy(InputStream inputStream, OutputStream outputStream) throws IOException {
		IOUtils.copy(inputStream, outputStream);
		inputStream.close();
		outputStream.close();
	}

	/**
	 * DOCME add JavaDoc for method getInputStream
	 * 
	 * @param source
	 *            {@link String} indicating resource or {@link File}
	 * @return {@link InputStream}
	 * @throws IOException
	 * @since DOCME add inception version number
	 */
	public static InputStream getInputStream(String source) throws IOException {
		InputStream is = IOStreamUtil.class.getResourceAsStream(source);

		if (is != null) {
			return is;
		}

		if (!source.startsWith("/")) {
			is = IOStreamUtil.class.getResourceAsStream("/" + source);

			if (is != null) {
				return is;
			}
		}

		return new FileInputStream(source);
	}

	/**
	 * DOCME add JavaDoc for method toString
	 * 
	 * @param inputStream
	 *            {@link InputStream}
	 * @return {@link String}
	 * @throws IOException
	 * @since DOCME add inception version number
	 */
	public static String toString(InputStream inputStream) throws IOException {
		String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
		inputStream.close();

		return result;
	}
}
