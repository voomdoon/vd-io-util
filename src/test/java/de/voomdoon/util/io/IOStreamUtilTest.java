package de.voomdoon.util.io;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.voomdoon.testing.tests.TestBase;

/**
 * Tests for {@link IOStreamUtil}.
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
public class IOStreamUtilTest {

	/**
	 * Tests for {@link IOStreamUtil#copy(InputStream, OutputStream)}.
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	@Nested
	class CopyTest extends TestBase {

		// TODO add test for closed input stream

		@Test
		void test() throws Exception {
			logTestStart();

			FileOutputStream fos = new FileOutputStream(getTempDirectory() + "/output.txt");
			IOStreamUtil.copy(IOStreamUtil.getInputStream("test.txt"), fos);

			try {
				fos.write(0);// TODO improve check for closed output stream
				fail("Missing 'IOException'!");
			} catch (IOException e) {
				logger.debug("expected error: " + e.getMessage());
			}
		}
	}

	/**
	 * Tests for {@link IOStreamUtil#getInputStream(String)}.
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	@Nested
	class GetInputStreamTest extends TestBase {

		@Test
		void test_file() throws Exception {
			logTestStart();

			String fileName = getTempDirectory() + "/test.txt";
			IOStreamUtil.copy(IOStreamUtil.getInputStream("test.txt"), new FileOutputStream(fileName));

			InputStream actual = IOStreamUtil.getInputStream(fileName);
			assertThat(actual).isNotNull();

			String actualString = IOStreamUtil.toString(actual);
			assertThat(actualString).isEqualTo("hello\nBerlin");
		}

		/**
		 * @throws IOException
		 * @since 0.1.0
		 */
		@Test
		void test_resource_withLeadingSlash() throws IOException {
			logTestStart();

			InputStream actual = IOStreamUtil.getInputStream("/test.txt");
			assertThat(actual).isNotNull();

			String actualString = IOStreamUtil.toString(actual);
			assertThat(actualString).isEqualTo("hello\nBerlin");
		}

		/**
		 * @throws IOException
		 * @since 0.1.0
		 */
		@Test
		void test_resource_withoutLeadingSlash() throws IOException {
			logTestStart();

			InputStream actual = IOStreamUtil.getInputStream("test.txt");
			assertThat(actual).isNotNull();

			String actualString = IOStreamUtil.toString(actual);
			assertThat(actualString).isEqualTo("hello\nBerlin");
		}
	}

	/**
	 * Tests for {@link IOStreamUtil#toString(InputStream)}.
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	@Nested
	class ToString_InputStream_Test extends TestBase {

		// TODO add test for closed input stream

		/**
		 * @throws Exception
		 * @since 0.1.0
		 */
		@Test
		void test() throws Exception {
			logTestStart();

			String actual = IOStreamUtil.toString(new ByteArrayInputStream("test".getBytes(StandardCharsets.UTF_8)));
			assertThat(actual).isEqualTo("test");
		}
	}
}
