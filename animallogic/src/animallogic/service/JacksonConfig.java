package animallogic.service;

import java.text.SimpleDateFormat;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.codehaus.jackson.map.ObjectMapper;

@Provider
/*
 * JacksonConfig basic configurations for the Webservcies can be done in this class.
 * 
 * */
public class JacksonConfig implements ContextResolver<ObjectMapper>
{
    private final ObjectMapper objectMapper;

    public JacksonConfig() throws Exception
    {
        objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("MM/dd/yyyy HH:mm"));
    }

    @Override
    public ObjectMapper getContext(Class<?> arg0)
    {
        return objectMapper;
    }
 }