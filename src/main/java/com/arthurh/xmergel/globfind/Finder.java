package com.arthurh.xmergel.globfind;

import com.google.common.collect.Lists;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
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
public class Finder
        extends SimpleFileVisitor<Path> {

    private PathMatcher matcher;
    private List<Path> matchPath;

    public Finder() {
        matchPath = Lists.newArrayList();
    }

    // Compares the glob pattern against
    // the file or directory name.
    void find(Path file) {
        Path name = file.getFileName();
        if (name != null && matcher.matches(name)) {
            matchPath.add(file);
        }
    }


    // Invoke the pattern matching
    // method on each directory.
    @Override
    public FileVisitResult preVisitDirectory(Path dir,
                                             BasicFileAttributes attrs) {
        return FileVisitResult.CONTINUE;
    }

    // Invoke the pattern matching
    // method on each file.
    @Override
    public FileVisitResult visitFile(Path file,
                                     BasicFileAttributes attrs) {
        find(file);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file,
                                           IOException exc) {
        System.err.println(exc);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        return FileVisitResult.SKIP_SUBTREE;
    }

    public PathMatcher getMatcher() {
        return matcher;
    }

    public void setMatcher(PathMatcher matcher) {
        this.matcher = matcher;
    }

    public List<Path> getMatchPath() {
        return matchPath;
    }
}
