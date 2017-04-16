package com.andreldm.report.engine;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.lowagie.text.DocumentException;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.Map;

public class ReportEngine {
    private Handlebars handlebars;

    public ReportEngine() {
        handlebars = new Handlebars();
        handlebars.registerHelpers(new HandlebarsHelpers());
    }

    public void generate(String templateFile, String outputFile, Map<String, Object> data) {
        try (OutputStream output = new FileOutputStream(new File(outputFile))) {
            Template template = handlebars.compile(templateFile);

            String mergedTemplate = template.apply(data);
            Document doc = getDocumentBuilder().parse(new ByteArrayInputStream(mergedTemplate.getBytes("UTF-8")));

            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocument(doc, null);
            renderer.layout();
            renderer.createPDF(output);
            renderer.finishPDF();
        } catch (IOException | ParserConfigurationException | SAXException | DocumentException e) {
            e.printStackTrace();
        }
    }

    private DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
        DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
        fac.setNamespaceAware(false);
        fac.setValidating(false);
        fac.setFeature("http://xml.org/sax/features/namespaces", false);
        fac.setFeature("http://xml.org/sax/features/validation", false);
        fac.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
        fac.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        return fac.newDocumentBuilder();
    }
}

