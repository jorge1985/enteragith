package com.youandbbva.enteratv.saml2;

import org.joda.time.DateTime;
import org.opensaml.DefaultBootstrap;
import org.opensaml.common.IdentifierGenerator;
import org.opensaml.common.binding.BasicSAMLMessageContext;
import org.opensaml.common.impl.SecureRandomIdentifierGenerator;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.binding.encoding.HTTPRedirectDeflateEncoder;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.NameIDPolicy;
import org.opensaml.saml2.metadata.AssertionConsumerService;
import org.opensaml.saml2.metadata.Endpoint;
import org.opensaml.ws.message.BaseMessageContext;
import org.opensaml.ws.message.MessageContext;
import org.opensaml.ws.message.encoder.MessageEncodingException;
import org.opensaml.ws.transport.OutputStreamOutTransportAdapter;
import org.opensaml.ws.transport.http.HttpServletResponseAdapter;
import org.opensaml.xml.ConfigurationException;
import org.opensaml.xml.XMLObjectBuilder;
import org.opensaml.xml.XMLObjectBuilderFactory;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallerFactory;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.util.XMLObjectHelper;
import org.w3c.dom.Element;

import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;

/**
 * Created by evg77 on 6/12/15.
 */

public class LoginRequest {

    protected static AuthnRequest authnRequestBase;
    protected static IdentifierGenerator identifierGenerator;

    protected String id;
    protected AuthnRequest authnRequest;//temp

    static boolean init;

    /*private*/ LoginRequest() {

    }

    public static LoginRequest createNew() {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.getRedirectUrl();
        try {
            loginRequest.authnRequest = XMLObjectHelper.cloneXMLObject(LoginRequest.authnRequestBase);
            loginRequest.id = identifierGenerator.generateIdentifier();
            loginRequest.authnRequest.setID(loginRequest.id);
        } catch (MarshallingException e) {
            e.printStackTrace();
        } catch (UnmarshallingException e) {
            e.printStackTrace();
        }
        return loginRequest;
    }

    public String doSAMLRequest(HttpServletResponse res) {

        /*BaseMessageContext baseMessageContext = new BaseMessageContext();
        baseMessageContext.setInboundMessage(authnRequest);
        baseMessageContext.setOutboundMessageTransport(new OutputStreamOutTransportAdapter(System.out));*/

        XMLObjectBuilderFactory xmlObjectBuilderFactory = org.opensaml.Configuration.getBuilderFactory();

        XMLObjectBuilder<Endpoint> endPointXMLObjectBuilder = xmlObjectBuilderFactory.getBuilder(AssertionConsumerService.DEFAULT_ELEMENT_NAME);
        Endpoint endpoint = endPointXMLObjectBuilder.buildObject(AssertionConsumerService.DEFAULT_ELEMENT_NAME);
        endpoint.setLocation("https://win-q4qhqbmlfv3.dom.dom/adfs/ls/");

        BasicSAMLMessageContext baseMessageContext = new BasicSAMLMessageContext();
        baseMessageContext.setOutboundMessageTransport(new HttpServletResponseAdapter(res, true));
        baseMessageContext.setOutboundSAMLMessage(authnRequest);
        baseMessageContext.setPeerEntityEndpoint(endpoint);
        //baseMessageContext.getPeerEntityEndpoint().setLocation("https://win-q4qhqbmlfv3.dom.dom/adfs/ls/");

        HTTPRedirectDeflateEncoder httpRedirectDeflateEncoder = new HTTPRedirectDeflateEncoder();
        try {
            httpRedirectDeflateEncoder.encode(baseMessageContext);

        } catch (MessageEncodingException e) {
            e.printStackTrace();
        }

        return "";
    }

    public String getRedirectUrl() {

        try {
            DefaultBootstrap.bootstrap();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }

        //IdentifierGenerator identifierGenerator = null;
        try {
            identifierGenerator = new SecureRandomIdentifierGenerator();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        XMLObjectBuilderFactory xmlObjectBuilderFactory = org.opensaml.Configuration.getBuilderFactory();
        XMLObjectBuilder<AuthnRequest> authnRequestXMLObjectBuilder = xmlObjectBuilderFactory.getBuilder(AuthnRequest.DEFAULT_ELEMENT_NAME);
        AuthnRequest authnRequest = authnRequestXMLObjectBuilder.buildObject(AuthnRequest.DEFAULT_ELEMENT_NAME);
        authnRequest.setID(identifierGenerator.generateIdentifier());
        authnRequest.setIssueInstant(new DateTime());

        /*XMLObjectBuilder<NameIDPolicy> nameIDPolicyXMLObjectBuilder = xmlObjectBuilderFactory.getBuilder(NameIDPolicy.DEFAULT_ELEMENT_NAME);
        NameIDPolicy nameIDPolicy = nameIDPolicyXMLObjectBuilder.buildObject(NameIDPolicy.DEFAULT_ELEMENT_NAME);
        nameIDPolicy.setSPNameQualifier("https://yandex.ru/metadata.xml");
        //nameIDPolicy.setFormat("urn:oasis:names:tc:SAML:2.0:nameid-format:transient");
        nameIDPolicy.setFormat("urn:oasis:names:tc:SAML:1.1:nameid-format:emailAddress");

        nameIDPolicy.setAllowCreate(true);

        authnRequest.setNameIDPolicy(nameIDPolicy);*/

        XMLObjectBuilder<Issuer> issuerXMLObjectBuilder = xmlObjectBuilderFactory.getBuilder(Issuer.DEFAULT_ELEMENT_NAME);

        Issuer issuer = issuerXMLObjectBuilder.buildObject(Issuer.DEFAULT_ELEMENT_NAME);
        issuer.setValue("https://yandex.ru/metadata.xml");

        authnRequest.setIssuer(issuer);
        LoginRequest.authnRequestBase = authnRequest;

        MarshallerFactory marshallerFactory = org.opensaml.Configuration.getMarshallerFactory();

// Get the Subject marshaller
        Marshaller marshaller = marshallerFactory.getMarshaller(authnRequest);

// Marshall the Subject
        Element subjectElement = null;
        try {
            subjectElement = marshaller.marshall(authnRequest);
        } catch (MarshallingException e) {
            e.printStackTrace();
        }


        System.out.println(Util.XmlToString(subjectElement, true));

        return null;
    }

    public static void main(String[] args) {
        LoginRequest loginRequest = LoginRequest.createNew();
        loginRequest.getRedirectUrl();

        //loginRequest.doSAMLRequest(new RespStub());
    }
}
