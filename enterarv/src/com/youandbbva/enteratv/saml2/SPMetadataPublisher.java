package com.youandbbva.enteratv.saml2;

import org.opensaml.DefaultBootstrap;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.metadata.*;
import org.opensaml.xml.ConfigurationException;
import org.opensaml.xml.XMLObjectBuilder;
import org.opensaml.xml.XMLObjectBuilderFactory;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallerFactory;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.security.credential.UsageType;
import org.opensaml.xml.signature.KeyInfo;
import org.opensaml.xml.signature.X509Certificate;
import org.opensaml.xml.signature.X509Data;
import org.w3c.dom.Element;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by evg77 on 6/12/15.
 */
public class SPMetadataPublisher {

    protected static EntityDescriptor entityDescriptor;//get here template static init
    static boolean init;


    public static void main(String[] args) {
        new SPMetadataPublisher().generate();
    }

    //returns entityDescriptor, input String keySign, keyEncrypt
    protected void generate() {
        try {
            DefaultBootstrap.bootstrap();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }

        XMLObjectBuilderFactory xmlObjectBuilderFactory = org.opensaml.Configuration.getBuilderFactory();
        /*XMLObjectBuilder<AuthnRequest> authnRequestXMLObjectBuilder = xmlObjectBuilderFactory.getBuilder(AuthnRequest.DEFAULT_ELEMENT_NAME);
        AuthnRequest authnRequest = authnRequestXMLObjectBuilder.buildObject(AuthnRequest.DEFAULT_ELEMENT_NAME);
        authnRequest.setID("cracracra");

        XMLObjectBuilder<Issuer> issuerXMLObjectBuilder = xmlObjectBuilderFactory.getBuilder(Issuer.DEFAULT_ELEMENT_NAME);

        Issuer issuer = issuerXMLObjectBuilder.buildObject(Issuer.DEFAULT_ELEMENT_NAME);
        issuer.setValue("YA issuer!");

        authnRequest.setIssuer(issuer);*/
        XMLObjectBuilder<EntityDescriptor> entityDescriptorXMLObjectBuilder = xmlObjectBuilderFactory.getBuilder(EntityDescriptor.DEFAULT_ELEMENT_NAME);
        EntityDescriptor entityDescriptor = entityDescriptorXMLObjectBuilder.buildObject(EntityDescriptor.DEFAULT_ELEMENT_NAME);
        entityDescriptor.setEntityID("https://yandex.ru/metadata.xml");


        XMLObjectBuilder<SPSSODescriptor> spssoDescriptorXMLObjectBuilder = xmlObjectBuilderFactory.getBuilder(SPSSODescriptor.DEFAULT_ELEMENT_NAME);
        SPSSODescriptor spssoDescriptor = spssoDescriptorXMLObjectBuilder.buildObject(SPSSODescriptor.DEFAULT_ELEMENT_NAME);
        spssoDescriptor.addSupportedProtocol(SAMLConstants.SAML20P_NS);


        entityDescriptor.getRoleDescriptors().add(spssoDescriptor);

        List<KeyDescriptor> keyDescriptorList = new LinkedList<>();

        XMLObjectBuilder<KeyDescriptor> keyDescriptorXMLObjectBuilder = xmlObjectBuilderFactory.getBuilder(KeyDescriptor.DEFAULT_ELEMENT_NAME);
        KeyDescriptor keyDescriptorSign = keyDescriptorXMLObjectBuilder.buildObject(KeyDescriptor.DEFAULT_ELEMENT_NAME);
        keyDescriptorSign.setUse(UsageType.SIGNING);

        XMLObjectBuilder<KeyInfo> keyInfoXMLObjectBuilder = xmlObjectBuilderFactory.getBuilder(KeyInfo.DEFAULT_ELEMENT_NAME);
        KeyInfo keyInfoSign = keyInfoXMLObjectBuilder.buildObject(KeyInfo.DEFAULT_ELEMENT_NAME);
        KeyInfo keyInfoEncr = keyInfoXMLObjectBuilder.buildObject(KeyInfo.DEFAULT_ELEMENT_NAME);

        XMLObjectBuilder<X509Data> x509DataXMLObjectBuilder = xmlObjectBuilderFactory.getBuilder(X509Data.DEFAULT_ELEMENT_NAME);
        X509Data x509DataSign = x509DataXMLObjectBuilder.buildObject(X509Data.DEFAULT_ELEMENT_NAME);
        X509Data x509DataEncr = x509DataXMLObjectBuilder.buildObject(X509Data.DEFAULT_ELEMENT_NAME);

        XMLObjectBuilder<X509Certificate> x509CertificateXMLObjectBuilder = xmlObjectBuilderFactory.getBuilder(X509Certificate.DEFAULT_ELEMENT_NAME);
        X509Certificate x509CertificateSign = x509CertificateXMLObjectBuilder.buildObject(X509Certificate.DEFAULT_ELEMENT_NAME);
        x509CertificateSign.setValue("MIIDbTCCAlWgAwIBAgIJANhcutAKjoGlMA0GCSqGSIb3DQEBBQUAME0xCzAJBgNV\n" +
                "BAYTAlJVMQ8wDQYDVQQIDAZNb3Njb3cxDzANBgNVBAcMBk1vc2NvdzEcMBoGA1UE\n" +
                "CgwTRGVmYXVsdCBDb21wYW55IEx0ZDAeFw0xNTA2MTUxMjE2MDdaFw0xNjA2MTQx\n" +
                "MjE2MDdaME0xCzAJBgNVBAYTAlJVMQ8wDQYDVQQIDAZNb3Njb3cxDzANBgNVBAcM\n" +
                "Bk1vc2NvdzEcMBoGA1UECgwTRGVmYXVsdCBDb21wYW55IEx0ZDCCASIwDQYJKoZI\n" +
                "hvcNAQEBBQADggEPADCCAQoCggEBAJ98PvmLF/88xJKeH8yZx4ge1jXo9rVm0i5z\n" +
                "/UCH8JUd7k2Mi1Hzj9fy15+NmpBNNP6XSu3o6nPV3Ypyt4QtZCiiEKZyeybzfKI0\n" +
                "VVzEER1ZJSw9uRAbneJ0H/lKjm2nm/seuxY5sfs/ikAloyFboL8URofHp5YUamry\n" +
                "ZjrTG/EN8i/vwCymuo36dK7uhNzCLNWciu2tshNxLthmuKuNA8kbYT3SRNydd4pN\n" +
                "QXSxlw4qJE2V7u1SvjgM4taysVpkRCb7pWqhUzkuqfuINzQatxYJ62oIw1pYyWPT\n" +
                "slykZ+OTNeWYKhH4zIqIluIPgzL7Qic+kFIYzljyjU86iQzDiZcCAwEAAaNQME4w\n" +
                "HQYDVR0OBBYEFMj27zob021vyHEctrgs26NQT4v7MB8GA1UdIwQYMBaAFMj27zob\n" +
                "021vyHEctrgs26NQT4v7MAwGA1UdEwQFMAMBAf8wDQYJKoZIhvcNAQEFBQADggEB\n" +
                "AJ31wyE4WnPr/IR4/CG/Dw+r5K2cxvBDT39kjon7jizyZqxdD9FXnAcGLYDa9LFV\n" +
                "QdStQo6ZM0/odvOqvcZ1Eh99LVJlxvoj4yIdv4jlfdbd+Pny4MUb0JMwCeLAQ/IX\n" +
                "ebu0t5obZaj7Mv+GDw0An9Mxag6b8oSG78YVpr8q8+bqHRDIn/pvJ0zKEl+e8Bj0\n" +
                "acyyk5iMP6ffeBisyFdhYM5WjAwPyqc62lnA6X4Gx9hCR5y9PQny9qWluznQkpYx\n" +
                "8f1+TczrI+KVYWJYR4g+DUELcTJ1+5NgfW+U6EJz/MWmZNIPZkl8kFEl6EGkeNgl\n" +
                "JfHmGsoabplI3P1OarCh1V0=");
        X509Certificate x509CertificateEncr = x509CertificateXMLObjectBuilder.buildObject(X509Certificate.DEFAULT_ELEMENT_NAME);
        x509CertificateEncr.setValue("MIIDbTCCAlWgAwIBAgIJANhcutAKjoGlMA0GCSqGSIb3DQEBBQUAME0xCzAJBgNV\n" +
                "BAYTAlJVMQ8wDQYDVQQIDAZNb3Njb3cxDzANBgNVBAcMBk1vc2NvdzEcMBoGA1UE\n" +
                "CgwTRGVmYXVsdCBDb21wYW55IEx0ZDAeFw0xNTA2MTUxMjE2MDdaFw0xNjA2MTQx\n" +
                "MjE2MDdaME0xCzAJBgNVBAYTAlJVMQ8wDQYDVQQIDAZNb3Njb3cxDzANBgNVBAcM\n" +
                "Bk1vc2NvdzEcMBoGA1UECgwTRGVmYXVsdCBDb21wYW55IEx0ZDCCASIwDQYJKoZI\n" +
                "hvcNAQEBBQADggEPADCCAQoCggEBAJ98PvmLF/88xJKeH8yZx4ge1jXo9rVm0i5z\n" +
                "/UCH8JUd7k2Mi1Hzj9fy15+NmpBNNP6XSu3o6nPV3Ypyt4QtZCiiEKZyeybzfKI0\n" +
                "VVzEER1ZJSw9uRAbneJ0H/lKjm2nm/seuxY5sfs/ikAloyFboL8URofHp5YUamry\n" +
                "ZjrTG/EN8i/vwCymuo36dK7uhNzCLNWciu2tshNxLthmuKuNA8kbYT3SRNydd4pN\n" +
                "QXSxlw4qJE2V7u1SvjgM4taysVpkRCb7pWqhUzkuqfuINzQatxYJ62oIw1pYyWPT\n" +
                "slykZ+OTNeWYKhH4zIqIluIPgzL7Qic+kFIYzljyjU86iQzDiZcCAwEAAaNQME4w\n" +
                "HQYDVR0OBBYEFMj27zob021vyHEctrgs26NQT4v7MB8GA1UdIwQYMBaAFMj27zob\n" +
                "021vyHEctrgs26NQT4v7MAwGA1UdEwQFMAMBAf8wDQYJKoZIhvcNAQEFBQADggEB\n" +
                "AJ31wyE4WnPr/IR4/CG/Dw+r5K2cxvBDT39kjon7jizyZqxdD9FXnAcGLYDa9LFV\n" +
                "QdStQo6ZM0/odvOqvcZ1Eh99LVJlxvoj4yIdv4jlfdbd+Pny4MUb0JMwCeLAQ/IX\n" +
                "ebu0t5obZaj7Mv+GDw0An9Mxag6b8oSG78YVpr8q8+bqHRDIn/pvJ0zKEl+e8Bj0\n" +
                "acyyk5iMP6ffeBisyFdhYM5WjAwPyqc62lnA6X4Gx9hCR5y9PQny9qWluznQkpYx\n" +
                "8f1+TczrI+KVYWJYR4g+DUELcTJ1+5NgfW+U6EJz/MWmZNIPZkl8kFEl6EGkeNgl\n" +
                "JfHmGsoabplI3P1OarCh1V0=");

        x509DataSign.getX509Certificates().add(x509CertificateSign);
        x509DataEncr.getX509Certificates().add(x509CertificateEncr);

        keyInfoSign.getX509Datas().add(x509DataSign);
        keyInfoEncr.getX509Datas().add(x509DataEncr);

        keyDescriptorSign.setKeyInfo(keyInfoSign);

        KeyDescriptor keyDescriptorEncr = keyDescriptorXMLObjectBuilder.buildObject(KeyDescriptor.DEFAULT_ELEMENT_NAME);
        keyDescriptorEncr.setUse(UsageType.ENCRYPTION);

        keyDescriptorEncr.setKeyInfo(keyInfoEncr);

        keyDescriptorList.add(keyDescriptorSign);
        keyDescriptorList.add(keyDescriptorEncr);

        spssoDescriptor.getKeyDescriptors().addAll(keyDescriptorList);

        XMLObjectBuilder<SingleLogoutService> singleLogoutServiceXMLObjectBuilder = xmlObjectBuilderFactory.getBuilder(SingleLogoutService.DEFAULT_ELEMENT_NAME);
        SingleLogoutService singleLogoutService = singleLogoutServiceXMLObjectBuilder.buildObject(SingleLogoutService.DEFAULT_ELEMENT_NAME);
        singleLogoutService.setBinding(SAMLConstants.SAML2_REDIRECT_BINDING_URI);
        singleLogoutService.setLocation("locacac");


        XMLObjectBuilder<AssertionConsumerService> assertionConsumerServiceXMLObjectBuilder = xmlObjectBuilderFactory.getBuilder(AssertionConsumerService.DEFAULT_ELEMENT_NAME);
        AssertionConsumerService assertionConsumerService = assertionConsumerServiceXMLObjectBuilder.buildObject(AssertionConsumerService.DEFAULT_ELEMENT_NAME);
        assertionConsumerService.setBinding(SAMLConstants.SAML2_POST_BINDING_URI);
        assertionConsumerService.setLocation("dadad");
        assertionConsumerService.setIsDefault(true);
        //assertionConsumerService.setIndex(0);

        //AssertionConsumerService assertionConsumerService1 = assertionConsumerServiceXMLObjectBuilder.buildObject(AssertionConsumerService.DEFAULT_ELEMENT_NAME);
       // assertionConsumerService1.setBinding(SAMLConstants.SAML2_POST_BINDING_URI);
        //assertionConsumerService1.setLocation("cacacac");
       // assertionConsumerService1.setIndex(1);

        spssoDescriptor.getSingleLogoutServices().add(singleLogoutService);
        spssoDescriptor.getAssertionConsumerServices().add(assertionConsumerService);
        //spssoDescriptor.getAssertionConsumerServices().add(assertionConsumerService1);


        MarshallerFactory marshallerFactory = org.opensaml.Configuration.getMarshallerFactory();

// Get the Subject marshaller
        Marshaller marshaller = marshallerFactory.getMarshaller(entityDescriptor);

// Marshall the Subject
        Element subjectElement = null;
        try {
            subjectElement = marshaller.marshall(entityDescriptor);
        } catch (MarshallingException e) {
            e.printStackTrace();
        }


        System.out.println(Util.XmlToString(subjectElement, true));



    }

    protected String getXMLAsString() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root>there will be metadata</root>";
    }

    public void publish(HttpServletResponse httpServletResponse) {
        try {
            httpServletResponse.getWriter().write(getXMLAsString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
