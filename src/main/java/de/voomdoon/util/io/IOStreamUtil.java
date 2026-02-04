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
 * Utility for IO streams.
 *
 * @author André Schulz
 *
 * @since 0.1.0
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
	 * @since 0.1.0
	 */
	public static void copyAndClose(InputStream inputStream, OutputStream outputStream) throws IOException {
		IOUtils.copy(inputStream, outputStream);
		inputStream.close();
		outputStream.close();
	}

	/**
	 * Returns an {@link InputStream} from a given source. Supported sources are:
	 * <ul>
	 * <li>Classpath resource (e.g. "/path/to/resource.txt", with optional leading '/')</li>
	 * <li>File (e.g. "/path/to/resource.txt")</li>
	 * <ul>
	 * 
	 * @param source
	 *            {@link String} indicating resource or {@link File}
	 * @return {@link InputStream}
	 * @throws IOException
	 * @since 0.1.0
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
	 * Converts an {@link InputStream} to a {@link String} using UTF-8 encoding and closes the stream.
	 * 
	 * @param inputStream
	 *            {@link InputStream}
	 * @return {@link String}
	 * @throws IOException
	 * @since 0.1.0
	 */
	public static String toStringAndClose(InputStream inputStream) throws IOException {
		String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
		inputStream.close();

		return result;
	}
}
