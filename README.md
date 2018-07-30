# Request Tracing-using-seluth-rabbitMQ-zipkin

#Log Aggreation : why it is needed?
 While following microservices architecture for our application a request goes through various services to complete a request .
 if any error occurs it will be very hard to go and check logs messages in each services to find the origin of the error. 
 so instead we aggregate our logs to a centralized server where we can trace our request path , store logs of all the services of our 
 microservices application. and here zipkin comes to our rescue.
 
