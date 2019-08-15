# samo-reactive-sandox
reactive sandbox for anything wherever 

# Secrutiy Configuration
* Set up a web application through [Okta account] (https://developer.okta.com/)  
* Allowed Grant Types:  
  ```
  Authorization Code
  Implicit(Hybrid) for both ID and Access Token  
  ```  
* Redirect URi's:  
  ```
  http://localhost:8080/login/oauth2/code/okta	
  http://localhost:3000/implicit/callback  
  ```
* Set following Environment Variable's with Okta security information  
```
export REACT_APP_OKTA_ISSUER_URI=https://dev-548917.okta.com/oauth2/default
export REACT_APP_SPRING_REACTIVE_SANDBOX_CLIENT_ID={client-id)
export REACT_APP_SPRING_REACTIVE_SANDBOX_CLIENT_SECRET={client-secret}
```  

## OKTA web application configuration example:    
![sec](resources/images/sec.png)  


References: 
* [Spring Docs](https://docs.spring.io/spring-framework/docs/5.0.0.BUILD-SNAPSHOT/spring-framework-reference/html/web-reactive.html)  
* [Blog from Okta](https://developer.okta.com/blog/2018/09/21/reactive-programming-with-spring)     

