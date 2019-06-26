package com.lab2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AppTest {

    @Test
    public void shouldAnswerWithTrue() throws IOException {
        String expectedInfo = "testFile.txt\r\n" +
                "111 Wed Jun 26 14:54:52 MSK 2019 0\r\n" +
                "rwx 0 bytes\r\n";
        String[] arguments = { "-l", "-h", "-r", "-o", "outputTest.txt", "testFile.txt"};
        new fileProcessorLauncher().launch(arguments);
        String contents = new String(Files.readAllBytes(Paths.get("outputTest.txt")));
        assertEquals(contents, expectedInfo);
    }
}
