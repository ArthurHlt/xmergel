package com.arthurh.xmergel.configuration;

import be.hikage.xdt4j.XdtTransformer;
import org.dom4j.io.SAXReader;
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
public class XmlCombinerConfig {

    @Bean
    public XdtTransformer xdtTransformer() {
        return new XdtTransformer();
    }

    @Bean
    public SAXReader saxReader() {
        return new SAXReader();
    }

}
