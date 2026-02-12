package de.voomdoon.util.io;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;

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
	class CopyAndCloseTest {

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_inputStreamIsClosed() throws Exception {
			AtomicBoolean isClosed = new AtomicBoolean(false);

			InputStream inputStream = new ByteArrayInputStream("test".getBytes(StandardCharsets.UTF_8)) {
				@Override
				public void close() throws IOException {
					isClosed.set(true);
					super.close();
				}
			};

			OutputStream outputStream = new ByteArrayOutputStream();

			IOStreamUtil.copyAndClose(inputStream, outputStream);

			assertThat(isClosed.get()).isTrue();
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_outputStreamContent() throws Exception {
			String inputContent = "test123";
			InputStream inputStream = new ByteArrayInputStream(inputContent.getBytes(StandardCharsets.UTF_8));
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

			IOStreamUtil.copyAndClose(inputStream, outputStream);

			String outputContent = outputStream.toString(StandardCharsets.UTF_8.name());
			assertThat(outputContent).isEqualTo(inputContent);
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_outputStreamIsClosed() throws Exception {
			AtomicBoolean isClosed = new AtomicBoolean(false);
			InputStream inputStream = new ByteArrayInputStream("test".getBytes(StandardCharsets.UTF_8));

			OutputStream outputStream = new ByteArrayOutputStream() {
				@Override
				public void close() throws IOException {
					isClosed.set(true);
					super.close();
				}
			};

			IOStreamUtil.copyAndClose(inputStream, outputStream);
			assertThat(isClosed.get()).isTrue();
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

		/**
		 * @since 0.1.0
		 */
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
	class ToStringAndCloseTest {

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_inputStreamIsClosed() throws Exception {
			AtomicBoolean isClosed = new AtomicBoolean(false);

			InputStream inputStream = new ByteArrayInputStream("test".getBytes(StandardCharsets.UTF_8)) {
				@Override
				public void close() throws IOException {
					isClosed.set(true);
					super.close();
				}
			};

			IOStreamUtil.toStringAndClose(inputStream);

			assertThat(isClosed.get()).isTrue();
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_result() throws Exception {
			ByteArrayInputStream inputStream = new ByteArrayInputStream("test".getBytes(StandardCharsets.UTF_8));

			String actual = IOStreamUtil.toStringAndClose(inputStream);

			assertThat(actual).isEqualTo("test");
		}
	}
}