# Log-Aggregation-using-seluth-rabbitMQ-zipkin
Log Aggregation using Spring cloud Sleuth as log message producer, RabbitMQ as message broker for asynchronous logging and Zipkin server as accepting log and visualizing tracing

#Log Aggreation : why it is needed?
 While following microservices architecture for our application a request goes through various services to complete a request .
 if any error occurs it will be very hard to go and check logs messages in each services to find the origin of the error. 
 so instead we aggregate our logs to a centralized server where we can trace our request path , store logs of all the services of our 
 microservices application. and here zipkin comes to our rescue.
 
 + logs can be aggreated using two way one using simple http to send logs like the rest end points use  or we can use 
 message brokers like rabbitmq to receive the log messages and delegate to the zipkin server when the zipkin server is availble. 
 the adavantage of using a message broker will even if our zipkin server is down the message broker (say rabbitmq or kafka) will store 
 the messages and later send it the zipkin server when it will be availble ( assuming that the message broker will always remain running).
 
 
