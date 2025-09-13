package org.example;

import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.apache.fop.apps.FOUserAgent;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.OutputStream;

public class FopGenerator {
    public static void main(String[] args) {
        try {
            // Initialize FOP factory
            FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
            FOUserAgent foUserAgent = fopFactory.newFOUserAgent();

            // Input FO file
            File foFile = new File("src/main/resources/sample.fo");

            // Output PDF file
            File pdfFile = new File("output.pdf");
            try (OutputStream out = new java.io.FileOutputStream(pdfFile)) {
                Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
                TransformerFactory factory = TransformerFactory.newInstance();
                Transformer transformer = factory.newTransformer();
                transformer.transform(new StreamSource(foFile), new SAXResult(fop.getDefaultHandler()));
            }

            // Output AFP file
            File afpFile = new File("output.afp");
            try (OutputStream out = new java.io.FileOutputStream(afpFile)) {
                Fop fop = fopFactory.newFop(MimeConstants.MIME_AFP, foUserAgent, out);
                TransformerFactory factory = TransformerFactory.newInstance();
                Transformer transformer = factory.newTransformer();
                transformer.transform(new StreamSource(foFile), new SAXResult(fop.getDefaultHandler()));
            }

            // Output HTML file
            File htmlFile = new File("output.tiff");
            try (OutputStream out = new java.io.FileOutputStream(htmlFile)) {
                Fop fop = fopFactory.newFop(MimeConstants.MIME_TIFF, foUserAgent, out);
                TransformerFactory factory = TransformerFactory.newInstance();
                Transformer transformer = factory.newTransformer();
                transformer.transform(new StreamSource(foFile), new SAXResult(fop.getDefaultHandler()));
            }

            System.out.println("PDF and AFP files generated successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}