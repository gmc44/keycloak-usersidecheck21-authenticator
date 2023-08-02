# keycloak-usersidecheck-authenticator

This project is a simple way to call a request during the client authentication flow and copy the result into an authentication note

## Build 

### Git Clone
`git clone https://github.com/gmc44/keycloak-usersidecheck-authenticator.git`

### Package
To build the JAR module, invoke
```sh
mvn package
```

This will download all required dependencies and build the JAR in the `target` directory.

## Installation

1. Create a new directory `providers` in your Keycloak installation dir (if not already existing).
2. Restart keycloak

A new "UserSideCheck" is then available in the authentication flow configuration.

## Usage example

Test if the user's web browser is on a secure intranet :

- the plugin load usersidecheck.ftl
  - javascript try (status code = 200) to reach "User Side Check URL" + "[Optional] Request Param1 Name" -> result = response.text
  - if failed, result = "Request Fetch Failed Response Value"
  - if timeout (after "Request Timeout (ms)"), result = "Request Timeout Response Value"

- copy result in "Auth Note Name"

- Always return context.success

## Release History

* 1.0
  * Initial release
  
## Configuration page

![config view](doc/usersidecheck-config.png?raw=true "config view")