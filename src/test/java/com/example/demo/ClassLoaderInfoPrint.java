package com.example.demo;

import static java.nio.file.StandardOpenOption.WRITE;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

public class ClassLoaderInfoPrint {

	public static void main(String... args) throws IOException {

		System.err.println("系统类加载器：" + System.getProperty("java.module.path"));

		String dirPathStr = "src/test/txt/";
		var now = LocalDateTime.now();
		var filePath = Paths.get(dirPathStr, now.format(DateTimeFormatter.ofPattern("yyyyMMddhhmmss")) + ".txt");
		System.err.println("文件目录：" + filePath.toAbsolutePath());

		var dirPath = Files.createDirectories(Path.of(dirPathStr));
		System.err.println("文件目录创建成功：" + dirPath.toAbsolutePath());

		Files.createFile(filePath);
		Files.newBufferedWriter(filePath, Charset.forName("UTF-8"), WRITE).append(getRandomStr(10)).flush();
		
		Stream<BigInteger> bigIntStream = Stream.iterate(BigInteger.ZERO, n -> n.add(BigInteger.ONE)).limit(10);
	    BigInteger[] bigIntArr = bigIntStream.toArray(BigInteger[]::new);
	    System.err.println(Arrays.toString(bigIntArr));
		
		System.err.println("done");
	}

	private static String getRandomStr(int length) {

		var strB = new StringBuilder(length);
		var random = new Random();
		for (int i = 0; i < length; i++) {

			strB.append(random.nextInt((char) 62));
		}

		return strB.toString();
	}
}
