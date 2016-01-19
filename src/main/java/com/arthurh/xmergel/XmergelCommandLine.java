package com.arthurh.xmergel;

import com.arthurh.xmergel.globfind.Finder;
import com.google.common.collect.Lists;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2016 Arthur Halet
 * <p>
 * This software is distributed under the terms and conditions of the 'MIT'
 * license which can be found in the file 'LICENSE' in this package distribution
 * or at 'http://opensource.org/licenses/MIT'.
 * <p>
 * Author: Arthur Halet
 * Date: 12/01/2016
 */
@Controller
public class XmergelCommandLine implements CommandLineRunner {

    @Autowired
    @Qualifier("options")
    private Options options;

    @Autowired
    @Qualifier("Helpformatter")
    private HelpFormatter helpFormatter;

    @Autowired
    @Qualifier("commandLineParser")
    private CommandLineParser commandLineParser;

    @Autowired
    @Qualifier("xmergel")
    private Xmergel xmergel;

    private Boolean isDebug = false;

    private List<Path> getPathsFromArgs(String... remainingArgs) {
        if (remainingArgs.length == 1) {
            return new ArrayList<Path>();
        }
        List<Path> filesToCombine = Lists.newArrayList();
        for (int i = 0; i < remainingArgs.length - 1; i++) {
            try {
                filesToCombine.add(Paths.get(remainingArgs[i]));
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                if (this.isDebug) {
                    e.printStackTrace();
                }
                System.exit(1);
            }
        }
        return filesToCombine;
    }

    private List<Path> getPathsFromFile(String file, List<Path> filesToCombine) throws IOException {
        Path filePath = Paths.get(file);
        File fileLister = filePath.toFile();
        BufferedReader br = new BufferedReader(new FileReader(fileLister));
        String line;
        PathMatcher matcher;
        Finder finder = new Finder();
        String parentFolder;
        Path filePathToCombine;
        while ((line = br.readLine()) != null) {
            if (line.isEmpty()) {
                continue;
            }
            parentFolder = "";
            filePathToCombine = Paths.get(line);
            if (filePathToCombine.getParent() != null) {
                parentFolder = filePathToCombine.getParent().toString();
            }
            matcher = FileSystems.getDefault()
                    .getPathMatcher("glob:" + filePathToCombine.getFileName().toString());
            finder.setMatcher(matcher);
            Files.walkFileTree(Paths.get(fileLister.getAbsoluteFile().getParent() + parentFolder), finder);

        }
        filesToCombine.addAll(finder.getMatchPath());
        return filesToCombine;
    }

    @Override
    public void run(String... args) throws Exception {
        CommandLine line = this.commandLineParser.parse(this.options, args);
        String[] remainingArgs = line.getArgs();

        if (remainingArgs.length < 3 && !line.hasOption("file") || remainingArgs.length < 1 && line.hasOption("file")) {
            helpFormatter.printHelp("xmergel [OPTION] [file1 file2 ...] [result_file]", this.options);
            System.exit(1);
        }
        if (line.hasOption("debug")) {
            this.isDebug = true;
        }
        List<Path> filesToCombine = this.getPathsFromArgs(remainingArgs);
        if (line.hasOption("file")) {
            System.out.println("Finding files...");
            filesToCombine = this.getPathsFromFile(line.getOptionValue("file"), filesToCombine);
        }
        String output = remainingArgs[remainingArgs.length - 1];
        System.out.println("Merging files...");
        try {
            this.xmergel.setResultFile(output);
            this.xmergel.combine(filesToCombine);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            if (this.isDebug) {
                e.printStackTrace();
            }
            System.exit(1);
        }
        File fileOutput = Paths.get(output).toFile();
        System.out.println("Files have been merged in '" + fileOutput.getAbsolutePath() + "'.");
    }

}
