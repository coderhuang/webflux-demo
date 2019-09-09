package com.example.demo.concurrency;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.nio.CharBuffer;
import java.time.LocalDateTime;

import org.junit.Test;

public class PipedIoTest {
	
	@Test
	public void ioCommunicateTest() throws IOException, InterruptedException {
		
		var reader = new PipedReader();
		var writer = new PipedWriter();
//		writer.connect(reader);
		reader.connect(writer);
		
		Runnable readWork = () -> {
			System.err.println("pipedReaderWorker start: " + LocalDateTime.now());
			CharBuffer cb = CharBuffer.allocate(128);
			cb.flip();// 测试个东西
			try (reader) {
				for (; reader.read(cb) > 0;) {
				}
				cb.flip();
				System.err.println(cb.toString());
				
				System.err.println("pipedReaderWorker end: " + LocalDateTime.now());
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		};
		
		Runnable writeWorker = () -> {
			
			System.err.println("PipedWriterWorker start: " + LocalDateTime.now());
			try (writer) {
				var messgae = LocalDateTime.now() + "-ahahaa";
				writer.write(messgae);
				System.err.println("write message:" + messgae);
				System.err.println("PipedWriterWorker end: " + LocalDateTime.now());
			} catch (IOException e) {

				e.printStackTrace();
			}
		};
		
		var readerWorker = new Thread(readWork);
		var writerWorker = new Thread(writeWorker);
		readerWorker.start();
		writerWorker.start();
		
		readerWorker.join();
	}

}
