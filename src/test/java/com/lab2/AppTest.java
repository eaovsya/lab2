package com.lab2;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

public class AppTest {

    @Test
    public void shouldAnswerWithTrue() throws IOException {
        File file = new File("testFile.txt");
        String expectedInfo = "testFile.txt\r\n" +
                "111" + " " + new Date(file.lastModified()) + " " + "0\r\n" +
                "rwx 0 bytes\r\n";
        String[] arguments = { "-l", "-h", "-r", "-o", "outputTest.txt", "testFile.txt"};
        new FileProcessorLauncher().launch(arguments);
        String contents = new String(Files.readAllBytes(Paths.get("outputTest.txt")));
        assertEquals(contents, expectedInfo);
    }

    @Test
    public void folderTest() throws IOException {
        File fileTest2 = new File("testFolder/test2.txt");
        File fileTest1 = new File("testFolder/test1.txt");
        File fileFolder = new File("testFolder");
        String expectedInfo = "test2.txt\r\n" +
                "111 " + new Date(fileTest2.lastModified()) + " 26462\r\n" +
                "rwx 25,84 Kb\r\n" +
                "test1.txt\r\n" +
                "111 " + new Date(fileTest1.lastModified()) + " 0\r\n" +
                "rwx 0 bytes\r\n" +
                "testFolder\r\n" +
                "111 " + new Date(fileFolder.lastModified()) + " 0\r\n" +
                "rwx 0 bytes\r\n";
        String[] arguments = { "-l", "-h", "-r", "-o", "outputTestFolder.txt", "testFolder"};
        new FileProcessorLauncher().launch(arguments);
        String contents = new String(Files.readAllBytes(Paths.get("outputTestFolder.txt")));
        assertEquals(contents, expectedInfo);
    }
}
