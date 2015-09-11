package com.youandbbva.enteratv.saml2;

import org.opensaml.common.binding.BasicSAMLMessageContext;
import org.opensaml.saml2.binding.decoding.HTTPPostDecoder;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.LogoutResponse;
import org.opensaml.saml2.core.Response;
import org.opensaml.saml2.encryption.Decrypter;
import org.opensaml.ws.message.decoder.MessageDecodingException;
import org.opensaml.ws.transport.http.HttpServletRequestAdapter;
import org.opensaml.xml.encryption.InlineEncryptedKeyResolver;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallerFactory;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.security.keyinfo.StaticKeyInfoCredentialResolver;
import org.opensaml.xml.security.x509.BasicX509Credential;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.signature.SignatureValidator;
import org.opensaml.xml.validation.ValidationException;
import org.w3c.dom.Element;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by evg77 on 6/12/15.
 */
public class SAttributeResponse {

    protected Response response;

    protected Assertion assertion;

    /*private*/ SAttributeResponse() {

    }

    public static SAttributeResponse createNew() {
        return new SAttributeResponse();
    }


    public boolean isSuccess() {
        return response != null;
    }

    public void handleRequest(HttpServletRequest req) {

        BasicSAMLMessageContext baseMessageContext = new BasicSAMLMessageContext();
        baseMessageContext.setInboundMessageTransport(new HttpServletRequestAdapter(req));




        HTTPPostDecoder httpPostDecoder = new HTTPPostDecoder();
        try {
            httpPostDecoder.decode(baseMessageContext);
            httpPostDecoder.getClass();
        } catch (MessageDecodingException e) {
            e.printStackTrace();
        } catch (org.opensaml.xml.security.SecurityException e) {
            e.printStackTrace();
        }

        response = (Response)baseMessageContext.getInboundSAMLMessage();

        String pathtoPrivKey = "/WEB-INF/saml2/key.pk8";
        //    RandomAccessFile raf = null;
        PrivateKey privKey = null;
        PublicKey publicKey = null;
        try {
//            raf = new RandomAccessFile("/WEB-INF/saml2/key.pk8", "r");
            InputStream in = Util.servletContext.getResourceAsStream("/WEB-INF/saml2/key.pk8");

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int next = in.read();
            while (next > -1) {
                bos.write(next);
                next = in.read();
            }
            bos.flush();
            byte[] result = bos.toByteArray();
            //          byte[] buf = new byte[(int)raf.length()];
            //      raf.readFully(buf);
            //    raf.close();
            PKCS8EncodedKeySpec kspec = new PKCS8EncodedKeySpec(result);
            KeyFactory kf = KeyFactory.getInstance("RSA");

            privKey =  kf.generatePrivate(kspec);

            in = Util.servletContext.getResourceAsStream("/WEB-INF/saml2/public_key.der");

            bos = new ByteArrayOutputStream();
            int next2 = in.read();
            while (next2 > -1) {
                bos.write(next2);
                next2 = in.read();
            }
            bos.flush();
            byte[] result2 = bos.toByteArray();
            //          byte[] buf = new byte[(int)raf.length()];
            //      raf.readFully(buf);
            //    raf.close();
            KeySpec kspec2 = new X509EncodedKeySpec(result2);

            publicKey =  kf.generatePublic(kspec2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*
        X509Certificate x509Certificate = null;
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            //x509Certificate = (X509Certificate)certificateFactory.generateCertificate(new ByteArrayInputStream(str.getBytes()));

        } catch (CertificateException e) {
            e.printStackTrace();
        }*/




        BasicX509Credential credential = new BasicX509Credential();
        credential.setPrivateKey(privKey);
        //credential.setPublicKey(publicKey);



        StaticKeyInfoCredentialResolver skicr =
                new StaticKeyInfoCredentialResolver(credential);
        Decrypter decrypter = new Decrypter(null, skicr, new InlineEncryptedKeyResolver());
        decrypter.setRootInNewDocument(true);

        MarshallerFactory marshallerFactory = org.opensaml.Configuration.getMarshallerFactory();

// Get the Subject marshaller

        CertificateFactory f = null;
        Certificate certificate = null;
        try {
            f = CertificateFactory.getInstance("X.509");
        } catch (CertificateException e) {
            e.printStackTrace();
        }

        try {
            certificate = f.generateCertificate(Util.servletContext.getResourceAsStream("/WEB-INF/saml2/public_server.cert"));
        } catch (CertificateException e) {
            e.printStackTrace();
        }

        credential.setPublicKey(certificate.getPublicKey());
/*
        for(EncryptedAssertion encryptedAssertion : response.getEncryptedAssertions()) {
            try {
                assertion = decrypter.decrypt(encryptedAssertion);
                System.out.println("decrypted");
            } catch (DecryptionException e) {
                e.printStackTrace();
            }
        }*/
        Marshaller marshaller = marshallerFactory.getMarshaller(response);

        Signature signature = response.getSignature();


        SignatureValidator signatureValidator = new SignatureValidator(credential);
        try {
            signatureValidator.validate(signature);
        } catch (ValidationException e) {
            e.printStackTrace();
        }

// Marshall the Subject
        Element subjectElement = null;
        try {
            subjectElement = marshaller.marshall(response);
        } catch (MarshallingException e) {
            e.printStackTrace();
        }

        System.out.println(Util.XmlToString(subjectElement, true));
/*
        Marshaller marshaller2 = marshallerFactory.getMarshaller(response);
        Element subjectElement2 = null;
        try {
            subjectElement2 = marshaller.marshall(response);
        } catch (MarshallingException e) {
            e.printStackTrace();
        }
        System.out.println(Util.XmlToString(subjectElement2, true));*/
        //decrypter.de
        //KeyInfoCredentialResolver keyInfoCredentialResolver = new StaticKeyInfoCredentialResolver();


    }

    public boolean isValid() {
        return true;
    }

    public SamlSession getSession() {
        SamlSession samlSession = new SamlSession();
        samlSession.setSessionEndDate(assertion.getConditions().getNotOnOrAfter());
        return samlSession;
    }
}

