package fr.nanterre.jeff.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.nanterre.jeff.iservice.IFileManager;

public class FileManagerService implements IFileManager {
	private final Logger log = LoggerFactory.getLogger(FileManagerService.class);

	public String convertListStringToString(List<String> stringList) {
		Iterator<String> inputtmp = stringList.iterator();
		while (inputtmp.hasNext()) {
			log.info(inputtmp.next());
		}
		return String.join("", stringList);
	}

	@Override
	public Boolean compareFilesContent(InputStream inputStream1, InputStream inputStream2) throws IOException {
		ReadableByteChannel ch1 = Channels.newChannel(inputStream1);
		ReadableByteChannel ch2 = Channels.newChannel(inputStream2);

		ByteBuffer buf1 = ByteBuffer.allocateDirect(1024);
		ByteBuffer buf2 = ByteBuffer.allocateDirect(1024);

		try {
			while (true) {

				int n1 = ch1.read(buf1);
				int n2 = ch2.read(buf2);

				if (n1 == -1 || n2 == -1) {
					return n1 == n2;
				}

				buf1.flip();
				buf2.flip();

				for (int i = 0; i < Math.min(n1, n2); i++) {
					if (buf1.get() != buf2.get()) {
						return false;
					}
				}

				buf1.compact();
				buf2.compact();
			}

		} finally {
			if (inputStream1 != null) {
				inputStream1.close();
			}
			if (inputStream2 != null) {
				inputStream2.close();
			}
		}
	}

}
