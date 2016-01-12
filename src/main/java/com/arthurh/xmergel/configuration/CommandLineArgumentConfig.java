package com.arthurh.xmergel.configuration;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
@Configuration
public class CommandLineArgumentConfig {

    @Bean
    public Options options() {
        Options options = new Options();
        options.addOption("f", "file", true, "File containing files to merge.");
        options.addOption("d", "debug", false, "Mode debug.");
        return options;
    }

    @Bean
    public HelpFormatter Helpformatter() {
        return new HelpFormatter();
    }

    @Bean
    public CommandLineParser commandLineParser() {
        return new BasicParser();
    }
}
