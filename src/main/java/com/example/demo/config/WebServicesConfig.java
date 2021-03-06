package com.example.demo.config;


import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;


@EnableWs //Enable Spring Web Services
@Configuration //Spring Configuration
public class WebServicesConfig {

    // MessageDispatcherServlet
    // ApplicationContext
    // url -> /ws/*

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext context) {
        MessageDispatcherServlet messageDispatcherServlet = new MessageDispatcherServlet();
        messageDispatcherServlet.setApplicationContext(context);
        messageDispatcherServlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<MessageDispatcherServlet>(messageDispatcherServlet, "/ws/verie/*");
    }

    // /ws/anselme/students.wsdl
    // course-details.xsd
    @Bean(name = "students")
    public DefaultWsdl11Definition studentWsdl(XsdSchema studentSchema) {
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("StudentPort");
        definition.setTargetNamespace("https://rca.ac.rw/verie/soap-app/student");
        definition.setLocationUri("/ws/verie/");
        definition.setSchema(studentSchema);
        return definition;
    }
    @Bean(name = "courses")
    public DefaultWsdl11Definition courseWsdl(XsdSchema courseSchema) {
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("CoursePort");
        definition.setTargetNamespace("https://rca.ac.rw/verie/soap-app/courses");
        definition.setLocationUri("/ws/verie/");
        definition.setSchema(courseSchema);
        return definition;
    }

    @Bean
    public XsdSchema studentSchema() {
        return new SimpleXsdSchema(new ClassPathResource("student.xsd"));
    }

    @Bean
    public  XsdSchema courseSchema(){
        return new SimpleXsdSchema(new ClassPathResource("courses.xsd"));
    }
}

