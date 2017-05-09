# spring-cloud-demo
Testing on spring cloud usage

Example code is copy and clone from the server urls below.

Reference
 * http://www.baeldung.com/spring-cloud-configuration
 * https://cloud.spring.io/spring-cloud-config/spring-cloud-config.html
 
Create properties file
Property file format <client>-<env>.yml/.properties
mkdir ${user.home}/config-repo
create config-client-development.yml
Add user.role: developer to config-client-development.yml
create config-client-production.yml
Add user.role: user to config-client-production.yml
