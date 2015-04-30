This is a test project based on AngularJS and Spark.

AngularJS	- client side MVC framework.
Spark		- server side mini MVC framework under java + jetty.

MongoDB is set by default. 
Hibernate module also exist if someone prefer.

Setup:

	The project should be imported in Eclipse as maven project (see import.jpg).
	For a work with VK.com MongoDB is not used.
	In order to launch server you have to say maven next commands:
		Open "Debug Configurations" and set in Goals 'compile' for compile project(see mvn_compile.jpg).
		Goal 'exec:java' to start server in debug mode (see maven_exec_java.jpg).
	Thats all, localhost:8080 is working.
	Tomcat is not needed because you have Jetty.


WWW:
	AngularJS	- https://docs.angularjs.org/
	Spark		- 
http://sparkjava.com/documentation.html#getting-started
	Main info how it work see here 
https://blog.openshift.com/developing-single-page-web-applications-using-java-8-spark-mongodb-and-angularjs/
	