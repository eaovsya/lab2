package com.lab2;


import java.io.*;
import java.util.*;

class FileProcessor {
    private  final  boolean longInfo;
    private  final boolean humanReadableInfo;
    private  final  boolean reverseInfo;
    private  String outputFileName;
    private final String inputFileName;

    FileProcessor(boolean longInfo, boolean humanReadableInfo, boolean reverseInfo, String inputFileName) {
        this(longInfo, humanReadableInfo, reverseInfo, null, inputFileName);
    }

    FileProcessor(boolean longInfo, boolean humanReadableInfo, boolean reverseInfo, String outputFileName, String inputFileName) {
        this.longInfo = longInfo;
        this.humanReadableInfo = humanReadableInfo;
        this.reverseInfo = reverseInfo;
        this.outputFileName = outputFileName;
        this.inputFileName = inputFileName;
    }

    void getFileInfo() {
        final File file;
        List<String> paths = new ArrayList<>();
        List<String> fileNames = new ArrayList<>();
        String info = "";
        try {
            file = new File(inputFileName);
            fileNames.add(inputFileName);
            if(!file.exists()) {
                System.out.println("File " + inputFileName + " doesn't exist");
                return;
            }

            if(file.isDirectory()) fileNames.addAll(Arrays.asList(Objects.requireNonNull(file.list())));

            for(int i = 0; i < fileNames.size(); i++) {
                File tempFile = i==0?file:new File(inputFileName + "/" + fileNames.get(i));
                info = fileNames.get(i) + "\r\n";
                if(longInfo) {
                    info += getAcl(tempFile, true) + " " + new Date(tempFile.lastModified()) + " " + tempFile.length() + "\r\n";
                }
                if(humanReadableInfo) {
                    info += getAcl(tempFile,false) + " " + fileSize(tempFile.length()) + "\r\n";
                }
                paths.add(info);
            }
            if(reverseInfo) {
                Collections.reverse(paths);
            }
            if(outputFileName != null) {
                StringBuilder fileText = new StringBuilder();
                for(String path:paths) {
                   fileText.append(path);
                }
                writeIntoFile(fileText.toString());
                System.out.println("Info was stored in " + outputFileName + " successfully!");
            } else {
                for(String path:paths) {
                    System.out.println(path);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getAcl(File file, boolean bits) {
        StringBuilder result = new StringBuilder();
        result.append(bits ? file.canRead() ? "1" : "0" : file.canRead() ? "r" : "");
        result.append(bits ? file.canWrite() ? "1" : "0": file.canWrite() ? "w" : "");
        result.append(bits ? file.canExecute() ? "1" : "0": file.canExecute() ? "x" : "");
        return result.toString();
    }

    private String fileSize(long bytes) {
        float result = bytes;
        String resultString = bytes + " bytes";
        if(bytes > 1024) {
            result = (float)bytes/1024;
            resultString = String.format("%.02f", result) + " Kb";
        }
        if(result > 1024) {
            result = result/1024;
            resultString = String.format("%.02f", result) + " Mb";
        }
        if(result > 1024) {
            result = result/1024;
            resultString = String.format("%.02f", result) + " Gb";
        }
        return  resultString;
    }

    private void writeIntoFile(String info)
            throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName));
        writer.write(info);

        writer.close();
    }
}
