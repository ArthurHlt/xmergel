package com.arthurh.xmergel.configuration;

import com.arthurh.xmergel.Xmergel;
import org.atteo.xmlcombiner.XmlCombiner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.parsers.ParserConfigurationException;

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
public class XmlCombinerConfig {

    @Bean
    public XmlCombiner xmlCombiner() throws ParserConfigurationException {
        return new XmlCombiner();
    }
}
