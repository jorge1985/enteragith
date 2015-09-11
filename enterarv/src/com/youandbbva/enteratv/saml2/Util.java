package com.youandbbva.enteratv.saml2;

import com.youandbbva.enteratv.beans.LogHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

/**
 * Created by evg77 on 6/12/15.
 */
public class Util {

    public static ServletContext servletContext;

    static String XmlToString(Element elem, boolean prettyFormat) {

        Transformer transformer = null;
        StreamResult result = null;
        try {
            transformer = TransformerFactory.newInstance().newTransformer();
            if(prettyFormat) {
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            }
            result = new StreamResult(new StringWriter());
            DOMSource source = new DOMSource(elem);
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.getWriter().toString();
    }

    static Element InputStreamToDom(InputStream in) {
        DocumentBuilderFactory factory = null;
        DocumentBuilder builder = null;
        Document ret = null;

        try {
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        try {
            ret = builder.parse(new InputSource(in));
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret.getDocumentElement();
    }

    public static void initSAML2() {
        LogHandler.getInstance().info("SAML2", "init...");
        SPMetadataPublisher.init = true;
        LoginRequest.init = true;
    }

    public static byte[] inputStreamToByteArray(InputStream in) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int next = 0;
        try {
            next = in.read();
            while (next > -1) {
                bos.write(next);
                next = in.read();
            }
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bos.toByteArray();
    }
}
