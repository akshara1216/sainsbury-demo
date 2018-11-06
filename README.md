# sainsbury-demo
Application to parse the html url and return the product json object

Project: Sainsbury Product Scrape Demo
Description: The purpose of the application is to read the html page and scrape the defined products from the whole page to a json object.
Tools Used: Java 8 JDK ,Maven 3.5 Junit (4.12) ,Jackson (2.9.6),Jsoup (1.11.3) and Mockito (2.6.1), Spring (),PowerMock  (1.7.1) ,log4Jtool (2.6.1)

Note : Please make sure the Maven and JDK dependencies has been installed and the Environmental variable are set properly. 

Instructions to build and run the application 
Windows / Unix 
1.	Open the command window. 
2.	Change the directory using the "cd  " to the project home directory 
Ex : cd D:\sainsbury-demo\ 
3.	Once in the home directory build the project using the below command 
mvn clean install 
4.	Once the project is build, the jar application name . "sainsbury-demo-1.0.0-jar-with-dependencies.jar" should have been generated.
5.	Run the application using the below command
java -jar target/sainsbury-demo-1.0.0-jar-with-dependencies.jar
6.	The output json objects will be displayed on the console and further logging of the application can be found in the below log location :- 
 Log Location: D:\sainsbury-demo\ sainsbury_demo_app.log
