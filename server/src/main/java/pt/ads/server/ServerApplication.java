package pt.ads.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import pt.ads.server.serializers.DoubleRoundedSerializer;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}


	@Bean
	public ObjectMapper jsonObjectMapper() {
		return Jackson2ObjectMapperBuilder.json()
				.featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
				.serializerByType(double.class, new DoubleRoundedSerializer())
				.serializerByType(Double.class, new DoubleRoundedSerializer())
				.build();
	}

}
