package pt.ads.server.serializers;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class DoubleRoundedSerializer extends JsonSerializer<Double> {

	@Override
	public void serialize(Double value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		BigDecimal bd = new BigDecimal(Double.toString(value));
		bd = bd.setScale(3, RoundingMode.HALF_EVEN);
		gen.writeNumber(bd.doubleValue());
	}

}
