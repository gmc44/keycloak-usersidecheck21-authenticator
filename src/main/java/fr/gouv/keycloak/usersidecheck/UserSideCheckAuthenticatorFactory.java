package fr.gouv.keycloak.usersidecheck;

import java.util.ArrayList;
import java.util.List;

import org.keycloak.Config;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;


public class UserSideCheckAuthenticatorFactory
    implements AuthenticatorFactory
{

    public static final String ID = "keycloak-usersidecheck";

    private static final AuthenticationExecutionModel.Requirement[] REQUIREMENT_CHOICES = {
            AuthenticationExecutionModel.Requirement.REQUIRED,
            AuthenticationExecutionModel.Requirement.ALTERNATIVE,
            AuthenticationExecutionModel.Requirement.DISABLED
    };

    private static final List<ProviderConfigProperty> configProperties = new ArrayList<ProviderConfigProperty>();

    static {
        ProviderConfigProperty property;
        //API
        property = new ProviderConfigProperty();
        property.setName(UserSideCheckConstants.CONF_USERSIDECHECK_URL);
        property.setLabel("User Side Check URL");
        property.setType(ProviderConfigProperty.STRING_TYPE);
        property.setHelpText("Enter your User Side Request URL");
        configProperties.add(property);
        property = new ProviderConfigProperty();
        property.setName(UserSideCheckConstants.CONF_USERSIDECHECK_METHOD);
        property.setLabel("User Side Check HTTP Method");
        property.setType(ProviderConfigProperty.LIST_TYPE);
        List<String> methodsList = new ArrayList(3);
        methodsList.add("HEAD");
        methodsList.add("GET");
        methodsList.add("POST");
        property.setOptions(methodsList);
        property.setDefaultValue("GET");
        property.setHelpText("Enter your User Side Request HTTP Method");
        configProperties.add(property);
        property = new ProviderConfigProperty();
        property.setName(UserSideCheckConstants.CONF_USERSIDECHECK_PARAM1_NAME);
        property.setLabel("[Optional] Request Param1 Name");
        property.setType(ProviderConfigProperty.LIST_TYPE);
        List<String> param1List = new ArrayList(3);
        param1List.add("username");
        param1List.add("none");
        property.setOptions(param1List);
        property.setDefaultValue("username");
        property.setHelpText("Enter a parameter name for your User Side Request (e.g. : username)");
        configProperties.add(property);
        property = new ProviderConfigProperty();
        property.setName(UserSideCheckConstants.CONF_USERSIDECHECK_TIMEOUT);
        property.setLabel("Request Timeout (ms)");
        property.setType(ProviderConfigProperty.STRING_TYPE);
        property.setDefaultValue("500");
        property.setHelpText("fetch timeout in milliseconds");
        configProperties.add(property);
        property = new ProviderConfigProperty();
        property.setName(UserSideCheckConstants.CONF_USERSIDECHECK_TIMEOUT_RESPONSE);
        property.setLabel("Request Timeout Response Value");
        property.setType(ProviderConfigProperty.STRING_TYPE);
        property.setHelpText("Response value if fetch has reached timeout");
        configProperties.add(property);
        property = new ProviderConfigProperty();
        property.setName(UserSideCheckConstants.CONF_USERSIDECHECK_FAILED_FETCH_RESPONSE);
        property.setLabel("Request Fetch Failed Response Value");
        property.setType(ProviderConfigProperty.STRING_TYPE);
        property.setHelpText("Response value if fetch has failed (not 200)");
        configProperties.add(property);
        property = new ProviderConfigProperty();
        property.setName(UserSideCheckConstants.CONF_USERSIDECHECK_AUTH_NOTE);
        property.setLabel("Auth Note Name");
        property.setType(ProviderConfigProperty.STRING_TYPE);
        property.setHelpText("It will copy the User Side Request response to this auth note");
        configProperties.add(property);

    }

    @Override
    public Authenticator create(KeycloakSession session)
    {
        return new UserSideCheckAuthenticator();
    }

    @Override
    public String getId()
    {
        return ID;
    }

    @Override
    public String getReferenceCategory()
    {
        return "UserSideCheck";
    }

    @Override
    public boolean isConfigurable()
    {
        // return false;
        return true;
    }

    @Override
    public boolean isUserSetupAllowed()
    {
        return true;
    }

    @Override
    public AuthenticationExecutionModel.Requirement[] getRequirementChoices()
    {
        return REQUIREMENT_CHOICES;
    }

    @Override
    public String getDisplayType()
    {
        return "UserSideCheck";
    }

    @Override
    public String getHelpText()
    {
        return "UserSideCheck";
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return configProperties;
    }

    @Override
    public void init(Config.Scope config)
    {
        // not needed for current version
    }

    @Override
    public void postInit(KeycloakSessionFactory factory)
    {
        // not needed for current version
    }

    @Override
    public void close()
    {
        // not used for current version
    }

}