package de.voomdoon.util.io;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.voomdoon.testing.file.TempFileExtension;
import de.voomdoon.testing.file.TempOutputFile;

/**
 * Tests for {@link IOStreamUtil}.
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
public class IOStreamUtilTest {

	/**
	 * Tests for {@link IOStreamUtil#copyAndClose(InputStream, OutputStream)}.
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	@Nested
	@ExtendWith(TempFileExtension.class)
	class CopyTest {

		// TODO add test for closed input stream

		@Test
		void test(@TempOutputFile File file) throws Exception {
			FileOutputStream fos = new FileOutputStream(file);
			IOStreamUtil.copyAndClose(IOStreamUtil.getInputStream("test.txt"), fos);

			assertThatThrownBy(() -> fos.write(0))
                .isInstanceOf(IOException.class);
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
	@ExtendWith(TempFileExtension.class)
	class GetInputStreamTest {

		@Test
		void test_file(@TempOutputFile File file) throws Exception {
			IOStreamUtil.copyAndClose(IOStreamUtil.getInputStream("test.txt"), new FileOutputStream(file));

			InputStream actual = IOStreamUtil.getInputStream(file.getAbsolutePath());
			assertThat(actual).isNotNull();

			String actualString = IOStreamUtil.toStringAndClose(actual);
			assertThat(actualString).isEqualTo("hello\nBerlin");
		}

		/**
		 * @throws IOException
		 * @since 0.1.0
		 */
		@Test
		void test_resource_withLeadingSlash() throws IOException {
			InputStream actual = IOStreamUtil.getInputStream("/test.txt");
			assertThat(actual).isNotNull();

			String actualString = IOStreamUtil.toStringAndClose(actual);
			assertThat(actualString).isEqualTo("hello\nBerlin");
		}

		/**
		 * @throws IOException
		 * @since 0.1.0
		 */
		@Test
		void test_resource_withoutLeadingSlash() throws IOException {
			InputStream actual = IOStreamUtil.getInputStream("test.txt");
			assertThat(actual).isNotNull();

			String actualString = IOStreamUtil.toStringAndClose(actual);
			assertThat(actualString).isEqualTo("hello\nBerlin");
		}
	}

	/**
	 * Tests for {@link IOStreamUtil#toStringAndClose(InputStream)}.
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	@Nested
	class ToString_InputStream_Test {

		// TODO add test for closed input stream

		/**
		 * @throws Exception
		 * @since 0.1.0
		 */
		@Test
		void test() throws Exception {
			String actual = IOStreamUtil
					.toStringAndClose(new ByteArrayInputStream("test".getBytes(StandardCharsets.UTF_8)));
			assertThat(actual).isEqualTo("test");
		}
	}
}