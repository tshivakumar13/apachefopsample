 import org.apache.fop.apps.FOUserAgent;
 import org.apache.fop.apps.Fop;
 import org.apache.fop.apps.FopFactory;
 import org.apache.fop.apps.MimeConstants;
 import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

 import javax.xml.transform.Transformer;
 import javax.xml.transform.TransformerFactory;
 import javax.xml.transform.sax.SAXResult;
 import javax.xml.transform.stream.StreamSource;
 import java.io.*;
import java.util.Map;

public class FopGeneratorWithVariables {
    public static void main(String[] args) {
        try {
            // Initialize Velocity
            VelocityEngine velocityEngine = new VelocityEngine();
            velocityEngine.init();

            // Load the FO template
            String templatePath = "src/main/resources/sample2.fo";
            StringWriter writer = new StringWriter();

            try (BufferedReader reader = new BufferedReader(new FileReader(templatePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    writer.write(line + "\n");
                }
            }

            // Create a Velocity context and populate variables
            VelocityContext context = new VelocityContext();
            context.put("companyName", "Your Company Name");
            context.put("companyAddress", "1234 Main Street");
            context.put("companyCityStateZip", "City, State, ZIP");
            context.put("companyPhone", "123-456-7890");
            context.put("companyEmail", "info@company.com");
            context.put("currentDate", "2023-10-01");
            context.put("recipientName", "John Doe");
            context.put("recipientAddress", "5678 Another Street, City, State, ZIP");
            context.put("salutation", "Mr. Doe");
            context.put("item1", "Item 1");
            context.put("item1Description", "Description of Item 1");
            context.put("item2", "Item 2");
            context.put("item2Description", "Description of Item 2");
            context.put("signatureName", "Jane Smith");
            context.put("signatureTitle", "CEO");

            // Merge template with context
            StringWriter mergedWriter = new StringWriter();
            velocityEngine.evaluate(context, mergedWriter, "log", writer.toString());

            // Write the processed FO content to a temporary file
            File processedFoFile = new File("processed_sample2.fo");
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(processedFoFile))) {
                bufferedWriter.write(mergedWriter.toString());
            }
            FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
            FOUserAgent foUserAgent = fopFactory.newFOUserAgent();

            File pdfFile = new File("output.pdf");
            try (OutputStream out = new java.io.FileOutputStream(pdfFile)) {
                Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
                TransformerFactory factory = TransformerFactory.newInstance();
                Transformer transformer = factory.newTransformer();
                transformer.transform(new StreamSource(processedFoFile), new SAXResult(fop.getDefaultHandler()));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}