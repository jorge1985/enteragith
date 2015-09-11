package com.youandbbva.enteratv.saml2;

import org.joda.time.DateTime;

/**
 * Created by evg77 on 6/18/15.
 */

public class SamlSession {

    protected DateTime sessionEndDate;

    SamlSession() {}

    public boolean isActual() {
        return !sessionEndDate.isBeforeNow();
    }

    void setSessionEndDate(DateTime date) {
        sessionEndDate = date;
    }

}
