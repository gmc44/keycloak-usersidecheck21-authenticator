<title>Authentification renforc&eacute;e</title>
<#import "template.ftl" as layout>
<@layout.registrationLayout; section>
    <#if section = "form">
      <style>
      #kc-username {
        display: none;
      }
      </style>
      <h2>Analyse de votre réseau en cours...</h2>
      <p id="result"></p>
      <#--  <p>URL1=${url1}, uid=${attr}</p>  -->
      <form id="kc-form-client-request" class="${properties.kcFormClass!}" action="${url.loginAction}" method="post">
          <input id="requestresult" name="requestresult" type="hidden"/>
      </form>

      <script>
          const testUrl = "${url1}";

          async function urlExists(url) {
              try {
                  const response = await fetch(url+"${attr}", { method: "${method}" });
                  const text = await response.text();
                  return text;
              } catch (error) {
                  return "${failedfetchresponse}";
              }
          }

          const timeoutPromise = new Promise((resolve, reject) => {
              setTimeout(() => {
                  resolve("${timeoutresponse}");
              }, ${timeout});
          });

          Promise.race([urlExists(testUrl), timeoutPromise]).then((result) => {
              //document.getElementById('result').textContent = result;
              document.getElementById('requestresult').value = result;
              document.getElementById('kc-form-client-request').submit();
          });
      </script>
      
  </#if>
</@layout.registrationLayout>