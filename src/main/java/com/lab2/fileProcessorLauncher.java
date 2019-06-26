package com.lab2;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;


public class fileProcessorLauncher
{
    @Option(name = "-l", aliases="--long", usage="Print rwx, last modification time and size in a bit mask.")
    private boolean longInfo;

    @Option(name = "-h", aliases="--human-readable", usage="Print rwx, last modification time and size.")
    private boolean humanReadableInfo;

    @Option(name = "-r", aliases="--reverse", usage="Reverses print order")
    private boolean reverseInfo;

    @Option(name = "-o", metaVar = "OutputName", aliases="--output", usage="Result file.")
    private String outputFileName;

    @Argument(required = true, metaVar = "InputName", usage = "Input file name")
    private String inputFileName;

    public static void main(String[] args) {
        new fileProcessorLauncher().launch(args);
    }

    void launch(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java -jar lab2.jar [-l] [-h] [-r] [-o output.file] directory_or_file");
            parser.printUsage(System.err);
            return;
        }

        fileProcessor fp = outputFileName != null ? new fileProcessor(longInfo, humanReadableInfo, reverseInfo, outputFileName, inputFileName):
                new fileProcessor(longInfo, humanReadableInfo, reverseInfo, inputFileName) ;
        fp.getFileInfo();
    }
}
