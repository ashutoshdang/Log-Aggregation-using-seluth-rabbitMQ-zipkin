# Request Tracing-using-seluth-rabbitMQ-zipkin

#Request Tracing : why it is needed?
 While following microservices architecture for our application a request goes through various services to complete a request .
 if any error occurs it will be very hard to go and check logs messages in each services to find the origin of the error. 
 so instead we aggregate our logs to a centralized server where we can trace our request path , store logs of all the services of our 
 microservices application. and here zipkin comes to our rescue.
 


<?xml version="1.0" encoding="UTF-8"?>
<configuration>
	<include resource="org/springframework/boot/logging/logback/defaults.xml"/>
	​
	<springProperty scope="context" name="springAppName" source="spring.application.name"/>
	<!-- Example for logging into the build folder of your project -->
	<property name="LOG_FILE" value="${BUILD_FOLDER:-build}/${springAppName}"/>​

	<!-- You can override this to have a custom pattern -->
	<property name="CONSOLE_LOG_PATTERN"
			  value="%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){faint} %clr(${LOG_LEVEL_PATTERN:-%5p}) %clr(${PID:- }){magenta} %clr(---){faint} %clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}"/>

	<!-- Appender to log to console -->
	<appender name="console" class="ch.qos.logback.core.ConsoleAppender">
		<filter class="ch.qos.logback.classic.filter.ThresholdFilter">
			<!-- Minimum logging level to be presented in the console logs-->
			<level>DEBUG</level>
		</filter>
		<encoder>
			<pattern>${CONSOLE_LOG_PATTERN}</pattern>
			<charset>utf8</charset>
		</encoder>
	</appender>

	<!-- Appender to log to file -->​
	<appender name="file"
		class="ch.qos.logback.core.rolling.RollingFileAppender">
		<rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
			<fileNamePattern>${LOG_PATH}LogsProducer.%d{yyyy-MM-dd}.log</fileNamePattern>
			<!-- <maxHistory>30</maxHistory> -->
			<totalSizeCap>3GB</totalSizeCap>
		</rollingPolicy>
		<encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
			<pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
		</encoder>
	</appender>
	​
	<!-- Appender to log to file in a JSON format -->
	  <appender name="amqp"
        class="org.springframework.amqp.rabbit.logback.AmqpAppender">
        <host>localhost</host>
        <port>5672</port>
        <virtualHost>/</virtualHost>
        <username>guest</username>
        <password>guest</password>
        <param name="virtualHost" value="/" />
        <exchangeType>topic</exchangeType>
        <exchangeName>ksplogexchange</exchangeName>
        <applicationId>logsProducer</applicationId>
        <routingKeyPattern>logs.elk</routingKeyPattern>
        <contentType>application/json</contentType>
        <maxSenderRetries>2</maxSenderRetries>
        <charset>UTF-8</charset>

		  <layout class="ch.qos.logback.contrib.json.classic.JsonLayout">
        <jsonFormatter
            class="ch.qos.logback.contrib.jackson.JacksonJsonFormatter">
            <prettyPrint>true</prettyPrint>
        </jsonFormatter>
        <timestampFormat>yyyy-MM-dd' 'HH:mm:ss.SSS</timestampFormat>
    </layout>
	</appender>
	​
	 <!--  
   appender for sending logs directly from the application to elastic search with out using
    anything in the middle to parse and format logs like the logstash or filebeat do 
    <appender name="log2es" class="de.agilecoders.logger.log2es.logback.ElasticsearchAppender">
      <fields>MESSAGE, THREAD, LEVEL,ARGUMENTS, LOGGER, MARKER,MDC, TIMESTAMP, STACKTRACE,CALLER, SERVICE, HOSTNAME
      </fields>
      <host>http://localhost:9200</host>
      <clientType>http</clientType>
      <gzip>true</gzip>
      <hostName>localhost</hostName>
      <serviceName>logsProducer</serviceName>
      <outgoingBulkSize>5000</outgoingBulkSize>
      <flushQueueTime>1 seconds</flushQueueTime>
    </appender> -->
<!-- 	<logger name="org.springframework" level="info" additivity="false"> -->
<!-- 		<appender-ref ref="console" /> -->
<!-- 		<appender-ref ref="amqp" /> -->
<!--       	<appender-ref ref="file" /> -->
<!-- 	</logger> -->
<!-- 	<logger name="org.hibernate" level="error" additivity="false"> -->
<!-- 		<appender-ref ref="console" /> -->
<!-- 		<appender-ref ref="amqp" /> -->
<!--       		<appender-ref ref="file" /> -->
<!-- 	</logger> -->
	
<!-- 	<logger name="org.apache" level="error" additivity="false"> -->
<!-- 		<appender-ref ref="console" /> -->
<!-- 		<appender-ref ref="amqp" /> -->
<!--       	<appender-ref ref="file" /> -->
<!-- 	</logger> -->
	
	<logger name="com.asu" level="info" additivity="false">
		<appender-ref ref="console" />
		<appender-ref ref="file" />
		<appender-ref ref="amqp" />
		<!-- <appender-ref ref="email" /> -->
	</logger>

	<root level="info">
		<appender-ref ref="console" />
		<appender-ref ref="file" />
		<appender-ref ref="amqp" />
		<!-- <appender-ref ref="email" /> -->
	</root>
	

</configuration>
