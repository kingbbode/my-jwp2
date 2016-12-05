package slipp.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.text.SimpleDateFormat;

/**
 * Created by YG-MAC on 2016. 12. 5..
 */
@Configuration
public class MyJwpApplicationConfig {
    @Bean
    public Jackson2ObjectMapperBuilder jacksonBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder() {
            @Override
            public void configure(ObjectMapper mapper) {
                mapper.setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE);
                mapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
                mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            }
        };
        builder.indentOutput(true).dateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        return builder;
    }
}
