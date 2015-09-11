package com.youandbbva.enteratv.saml2;

import com.youandbbva.enteratv.Registry;
import org.opensaml.saml2.metadata.provider.DOMMetadataProvider;
import org.w3c.dom.Element;


import javax.servlet.ServletContext;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Created by evg77 on 6/12/15.
 */
public class IdpMetadataExtractor {

    protected final static IdpMetadataExtractor instance = new IdpMetadataExtractor();

    public static ServletContext sc;

    protected DOMMetadataProvider domMetadataProvider;

    IdpMetadataExtractor() {}

    static {
        Registry registry = Registry.getInstance();
        Element metadataRoot = Util.InputStreamToDom(sc.getResourceAsStream(registry.getStringOfApplication("saml.idpmetadatapath")));
        instance.domMetadataProvider = new DOMMetadataProvider(metadataRoot);
    }

    public static IdpMetadataExtractor getInstance() {
        return instance;
    }

    public PublicKey getIdpPublicKeySignature() {
        return null;
    }

    public PublicKey getIdpPublicKeyEncryption() {
        return null;
    }

    public PrivateKey getSpPrivateKeySignature() {
        return null;
    }

    public String getIdpEndpoint() {

        return null;
    }
}
