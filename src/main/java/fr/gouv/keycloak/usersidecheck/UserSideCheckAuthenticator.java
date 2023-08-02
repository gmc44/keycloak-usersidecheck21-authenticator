package fr.gouv.keycloak.usersidecheck;

import static fr.gouv.keycloak.usersidecheck.UserSideCheckConstants.*;

import javax.ws.rs.core.MultivaluedMap;

import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.authenticators.browser.AbstractUsernameFormAuthenticator;
import org.keycloak.forms.login.LoginFormsProvider;
import org.keycloak.models.AuthenticatorConfigModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.services.ServicesLogger;

public class UserSideCheckAuthenticator extends AbstractUsernameFormAuthenticator
{
    public LoginFormsProvider loadForm(AuthenticationFlowContext context)
    {
        AuthenticatorConfigModel config = context.getAuthenticatorConfig();
        String url1 = config.getConfig().get(CONF_USERSIDECHECK_URL);
        String param1 = config.getConfig().get(CONF_USERSIDECHECK_PARAM1_NAME);
        String method = config.getConfig().get(CONF_USERSIDECHECK_METHOD);
        String timeout = config.getConfig().get(CONF_USERSIDECHECK_TIMEOUT);
        String timeoutresponse = config.getConfig().get(CONF_USERSIDECHECK_TIMEOUT_RESPONSE);
        String failedfetchresponse = config.getConfig().get(CONF_USERSIDECHECK_FAILED_FETCH_RESPONSE);

        //convert timeout en integer
        int timeoutInt;

        try {
            timeoutInt = Integer.parseInt(timeout);
        } catch (NumberFormatException e) {
            timeoutInt = 500;
        }

        LoginFormsProvider form = context.form();
        
        form.setAttribute("url1", url1);
        form.setAttribute("method", method);
        form.setAttribute("timeout", timeoutInt);
        form.setAttribute("timeoutresponse", timeoutresponse);
        form.setAttribute("failedfetchresponse", failedfetchresponse);
        ServicesLogger.LOGGER.info("param1="+param1);
        if (param1.equals("username")) {
            form.setAttribute("attr", context.getUser().getUsername());
        } else {
            form.setAttribute("attr", "");
        }

        return form;
    }

    @Override
    public void action(AuthenticationFlowContext context)
    {
        ServicesLogger.LOGGER.debug("UserSideCheck");
        // Get Form Input
        MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();

        String requestresult          = formData.getFirst("requestresult");
        
        LoginFormsProvider form = loadForm(context);
        
        if (requestresult != null) {
            AuthenticatorConfigModel config = context.getAuthenticatorConfig();
            String authnote = config.getConfig().get(CONF_USERSIDECHECK_AUTH_NOTE);
            ServicesLogger.LOGGER.debug("USC 2 rr="+requestresult);
            ServicesLogger.LOGGER.debug("USC 2 authnote="+authnote);
            context.getAuthenticationSession().setAuthNote(authnote, requestresult);
            context.success();
        } else {
            ServicesLogger.LOGGER.debug("USC 3");
            context.challenge(form.createForm(CONF_USERSIDECHECK_FTL));
        }
    }

    
    @Override
    public void authenticate(AuthenticationFlowContext context)
    {
        LoginFormsProvider form = loadForm(context);
        context.challenge(form.createForm(CONF_USERSIDECHECK_FTL));
    }

    @Override
    public boolean requiresUser()
    {
        return true;
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user)
    {
        return true;
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user)
    {
        // not needed for current version
    }
    

    @Override
    public void close()
    {
        // not used for current version
    }

}