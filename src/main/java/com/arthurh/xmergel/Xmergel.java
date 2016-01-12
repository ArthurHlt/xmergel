package com.arthurh.xmergel;

import org.atteo.xmlcombiner.XmlCombiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

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
@Component
public class Xmergel {
    @Autowired
    @Qualifier("xmlCombiner")
    private XmlCombiner xmlCombiner;

    private Path resultFile;

    public Xmergel() {
    }


    public void combine(Path... files) throws IOException, SAXException, TransformerException {
        for (Path file : files) {
            this.xmlCombiner.combine(file);
        }
        this.xmlCombiner.buildDocument(this.resultFile);
    }

    public XmlCombiner getXmlCombiner() {
        return xmlCombiner;
    }

    public void setXmlCombiner(XmlCombiner xmlCombiner) {
        this.xmlCombiner = xmlCombiner;
    }

    public Path getResultFile() {
        return resultFile;
    }

    public void setResultFile(String resultFile) {
        this.resultFile = Paths.get(resultFile);
    }

    public void setResultFile(Path resultFile) {
        this.resultFile = resultFile;
    }
}
