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



References: 
* [Spring Docs](https://docs.spring.io/spring-framework/docs/5.0.0.BUILD-SNAPSHOT/spring-framework-reference/html/web-reactive.html)  
* [Blog from Okta](https://developer.okta.com/blog/2018/09/21/reactive-programming-with-spring)     

