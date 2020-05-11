package org.magnum.mobilecloud.video.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.hateoas.Resources;

import java.io.IOException;

public class ResourcesMapper extends ObjectMapper {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// This anonymous inner class will handle conversion of the Spring Data Rest
// Resources objects into JSON. Resources are objects that Spring Data Rest
// creates with the Videos it obtains from your VideoRepository
    @SuppressWarnings("rawtypes")
    private JsonSerializer<Resources> serializer = new JsonSerializer<Resources>() {
        // We are going to register this class to handle all instances of type
// Resources
        @Override
        public Class<Resources> handledType() {
            return Resources.class;
        }

        @Override
        public void serialize(Resources value, JsonGenerator jgen,
                              SerializerProvider provider) throws IOException,
                JsonProcessingException {
// Extracted the actual data inside of the Resources object
// that we care about (e.g., the list of Video objects)
            Object content = value.getContent();
// Instead of all of the Resources member variables, etc.
// Just mashall the actual content (Videos) into the JSON
            JsonSerializer<Object> s = provider.findValueSerializer(
                    content.getClass(), null);
            s.serialize(content, jgen, provider);
        }
    };

    // Create an ObjectMapper and tell it to use our customer serializer
// to convert Resources objects into JSON
    public ResourcesMapper() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(serializer);
        registerModule(module);
    }
}