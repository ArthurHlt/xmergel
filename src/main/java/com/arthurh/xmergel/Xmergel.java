package com.arthurh.xmergel;

import be.hikage.xdt4j.XdtTransformer;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.transform.TransformerException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
@Component
public class Xmergel {
    @Autowired
    @Qualifier("xdtTransformer")
    private XdtTransformer xdtTransformer;

    @Autowired
    @Qualifier("saxReader")
    private SAXReader saxReader;


    private Path resultFile;

    public Xmergel() {
    }


    public void combine(List<Path> files) throws IOException, SAXException, TransformerException, DocumentException {
        Document finalDocument = this.saxReader.read(files.get(0).toFile());
        for (Path file : files) {
            finalDocument = this.xdtTransformer.transform(finalDocument, this.saxReader.read(file.toFile()));
        }
        OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter xmlWriter = new XMLWriter(
                new FileWriter(this.resultFile.toFile()),
                format
        );
        xmlWriter.write(finalDocument);
        xmlWriter.close();
    }

    public XdtTransformer getXdtTransformer() {
        return xdtTransformer;
    }

    public void setXdtTransformer(XdtTransformer xdtTransformer) {
        this.xdtTransformer = xdtTransformer;
    }

    public SAXReader getSaxReader() {
        return saxReader;
    }

    public void setSaxReader(SAXReader saxReader) {
        this.saxReader = saxReader;
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
