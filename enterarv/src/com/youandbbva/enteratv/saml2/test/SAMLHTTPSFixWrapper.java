package com.youandbbva.enteratv.saml2.test;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Created by evg77 on 6/14/15.
 */

public class SAMLHTTPSFixWrapper extends HttpServletRequestWrapper {
    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request
     * @throws IllegalArgumentException if the request is null
     */
    public SAMLHTTPSFixWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public StringBuffer getRequestURL() {
        return super.getRequestURL().delete(0, 4).insert(0, "https");
        //return new StringBuffer(super.getRequestURL().toString().replace("http", "https"));
    }

    public String getServletContext() {
        return super.getServletPath();
    }
}
