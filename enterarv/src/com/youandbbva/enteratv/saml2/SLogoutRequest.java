package com.youandbbva.enteratv.saml2;

import org.joda.time.DateTime;
import org.opensaml.DefaultBootstrap;
import org.opensaml.common.IdentifierGenerator;
import org.opensaml.common.binding.BasicSAMLMessageContext;
import org.opensaml.common.impl.SecureRandomIdentifierGenerator;
import org.opensaml.saml2.binding.encoding.HTTPRedirectDeflateEncoder;
import org.opensaml.saml2.core.EncryptedID;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.LogoutRequest;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.metadata.AssertionConsumerService;
import org.opensaml.saml2.metadata.Endpoint;
import org.opensaml.ws.message.encoder.MessageEncodingException;
import org.opensaml.ws.transport.http.HttpServletResponseAdapter;
import org.opensaml.xml.ConfigurationException;
import org.opensaml.xml.XMLObjectBuilder;
import org.opensaml.xml.XMLObjectBuilderFactory;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallerFactory;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.security.*;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.keyinfo.KeyInfoGenerator;
import org.opensaml.xml.security.keyinfo.KeyInfoGeneratorFactory;
import org.opensaml.xml.security.keyinfo.KeyInfoGeneratorManager;
import org.opensaml.xml.security.keyinfo.NamedKeyInfoGeneratorManager;
import org.opensaml.xml.security.x509.BasicX509Credential;
import org.opensaml.xml.security.x509.X509KeyInfoGeneratorFactory;
import org.opensaml.xml.signature.*;
import org.w3c.dom.Element;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.*;
import java.security.cert.X509Certificate;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by evg77 on 6/12/15.
 */
public class SLogoutRequest {

    protected LogoutRequest logoutRequest;

    protected Credential credential;

    SLogoutRequest() {}

    public static SLogoutRequest createNew() {
        return new SLogoutRequest();
    }

    public void doLogout() {

        try {
            DefaultBootstrap.bootstrap();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }

        IdentifierGenerator identifierGenerator = null;
        try {
            identifierGenerator = new SecureRandomIdentifierGenerator();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        XMLObjectBuilderFactory xmlObjectBuilderFactory = org.opensaml.Configuration.getBuilderFactory();

        XMLObjectBuilder<LogoutRequest> logoutRequestXMLObjectBuilder = xmlObjectBuilderFactory.getBuilder(LogoutRequest.DEFAULT_ELEMENT_NAME);
        LogoutRequest logoutRequest = logoutRequestXMLObjectBuilder.buildObject(LogoutRequest.DEFAULT_ELEMENT_NAME);


        XMLObjectBuilder<Issuer> issuerXMLObjectBuilder = xmlObjectBuilderFactory.getBuilder(Issuer.DEFAULT_ELEMENT_NAME);

        Issuer issuer = issuerXMLObjectBuilder.buildObject(Issuer.DEFAULT_ELEMENT_NAME);
        issuer.setValue("https://yandex.ru/metadata.xml");

        logoutRequest.setID(identifierGenerator.generateIdentifier());
        logoutRequest.setIssuer(issuer);
        logoutRequest.setIssueInstant(new DateTime());

        XMLObjectBuilder<NameID> nameIDXMLObjectBuilder = xmlObjectBuilderFactory.getBuilder(NameID.DEFAULT_ELEMENT_NAME);
        NameID nameID = nameIDXMLObjectBuilder.buildObject(NameID.DEFAULT_ELEMENT_NAME);

        nameID.setNameQualifier(null);
        nameID.setValue("dfgdg");

        logoutRequest.setNameID(nameID);

        EncryptedID encryptedID;


        XMLObjectBuilder<Signature> signatureXMLObjectBuilder = xmlObjectBuilderFactory.getBuilder(Signature.DEFAULT_ELEMENT_NAME);
        Signature signature = signatureXMLObjectBuilder.buildObject(Signature.DEFAULT_ELEMENT_NAME);

        PrivateKey privKey = null;
        PublicKey publicKey = null;
        X509Certificate x509Certificate = null;
        try {
//
            //FileInputStream in = new FileInputStream(new File("target/bbvatv/WEB-INF/saml2/key.pk8"));

            InputStream in = Util.servletContext.getResourceAsStream("/WEB-INF/saml2/key.pk8");

            PKCS8EncodedKeySpec kspec = new PKCS8EncodedKeySpec(Util.inputStreamToByteArray(in));
            KeyFactory kf = KeyFactory.getInstance("RSA");

            privKey = kf.generatePrivate(kspec);

            //InputStream in2 = new FileInputStream(new File("target/bbvatv/WEB-INF/saml2/cert.pem"));
            InputStream in2 = Util.servletContext.getResourceAsStream("/WEB-INF/saml2/cert.pem");

           /* KeySpec kspec2 = new X509EncodedKeySpec(Util.inputStreamToByteArray(in2));

            publicKey =  kf.generatePublic(kspec2);*/
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X509");
            x509Certificate = (X509Certificate) certificateFactory.generateCertificate(in2);

        }catch (Exception ex) {
            ex.printStackTrace();
        }

        BasicX509Credential basicX509Credential = new BasicX509Credential();

        this.credential = basicX509Credential;

        basicX509Credential.setPrivateKey(privKey);

        //basicX509Credential.setPublicKey(publicKey);
        basicX509Credential.setEntityCertificate(x509Certificate);

        signature.setSignatureAlgorithm(SignatureConstants.ALGO_ID_SIGNATURE_RSA_SHA1);
        signature.setCanonicalizationAlgorithm(SignatureConstants.ALGO_ID_C14N_OMIT_COMMENTS);
        signature.setSigningCredential(basicX509Credential);

        //logoutRequest.setSignature(signature);
logoutRequest.setDestination("https://win-q4qhqbmlfv3.dom.dom/adfs/ls");
        this.logoutRequest = logoutRequest;

        SecurityConfiguration secConfiguration = org.opensaml.Configuration.getGlobalSecurityConfiguration();
        NamedKeyInfoGeneratorManager namedKeyInfoGeneratorManager = secConfiguration.getKeyInfoGeneratorManager();
        KeyInfoGeneratorManager keyInfoGeneratorManager = namedKeyInfoGeneratorManager.getDefaultManager();

        KeyInfoGeneratorFactory keyInfoGeneratorFactory = keyInfoGeneratorManager.getFactory(basicX509Credential);
        //((X509KeyInfoGeneratorFactory)keyInfoGeneratorFactory).setEmitPublicKeyValue(true);
        KeyInfoGenerator keyInfoGenerator = keyInfoGeneratorFactory.newInstance();

        KeyInfo keyInfo = null;
        try {
            keyInfo = keyInfoGenerator.generate(basicX509Credential);
        } catch  (Exception e) {
            e.printStackTrace();
        }
       // signature.setKeyInfo(keyInfo);

/*
        MarshallerFactory marshallerFactory = org.opensaml.Configuration.getMarshallerFactory();
        Marshaller marshaller = marshallerFactory.getMarshaller(logoutRequest);



        Element subjectElement = null;
        try {
            subjectElement = marshaller.marshall(logoutRequest);
            Signer.signObject(signature);
        } catch (MarshallingException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        }*/

/*
        //System.out.println(Util.XmlToString(subjectElement, false));
        try {
            PrintWriter pw = new PrintWriter("target/lol.xml");
            pw.write(Util.XmlToString(subjectElement, false));
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/
    }

    public void doLogoutRequest(HttpServletResponse httpServletResponse) {

        doLogout();
        XMLObjectBuilderFactory xmlObjectBuilderFactory = org.opensaml.Configuration.getBuilderFactory();
        XMLObjectBuilder<Endpoint> endPointXMLObjectBuilder = xmlObjectBuilderFactory.getBuilder(AssertionConsumerService.DEFAULT_ELEMENT_NAME);
        Endpoint endpoint = endPointXMLObjectBuilder.buildObject(AssertionConsumerService.DEFAULT_ELEMENT_NAME);
        endpoint.setLocation("https://win-q4qhqbmlfv3.dom.dom/adfs/ls/");


        MarshallerFactory marshallerFactory = org.opensaml.Configuration.getMarshallerFactory();
        Marshaller marshaller = marshallerFactory.getMarshaller(logoutRequest);

        try {
            System.out.print(Util.XmlToString(marshaller.marshall(logoutRequest), true));
        } catch (MarshallingException e) {
            e.printStackTrace();
        }

        BasicSAMLMessageContext baseMessageContext = new BasicSAMLMessageContext();
        baseMessageContext.setOutboundMessageTransport(new HttpServletResponseAdapter(httpServletResponse, true));
        baseMessageContext.setOutboundSAMLMessage(logoutRequest);
        baseMessageContext.setPeerEntityEndpoint(endpoint);
        baseMessageContext.setOutboundSAMLMessageSigningCredential(credential);

        //baseMessageContext.getPeerEntityEndpoint().setLocation("https://win-q4qhqbmlfv3.dom.dom/adfs/ls/");

        HTTPRedirectDeflateEncoder httpRedirectDeflateEncoder = new HTTPRedirectDeflateEncoder();
        try {
            httpRedirectDeflateEncoder.encode(baseMessageContext);

        } catch (MessageEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new SLogoutRequest().doLogout();
    }
}
