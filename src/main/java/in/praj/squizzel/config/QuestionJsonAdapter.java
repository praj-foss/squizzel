/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package in.praj.squizzel.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import in.praj.squizzel.model.Question;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

/**
 * Handles serialization and deserialization of {@link Question}
 * objects for JSON.
 */
@JsonComponent
public class QuestionJsonAdapter {
    private static final class QuestionSerializer extends JsonSerializer<Question> {
        @Override
        public void serialize(Question value, JsonGenerator gen, SerializerProvider serializers)
                throws IOException {
            gen.writeStartObject();
            gen.writeFieldName("id");
            gen.writeNumber(value.getId());
            gen.writeFieldName("type");
            gen.writeString(value.getType());
            gen.writeFieldName("content");
            gen.writeRawValue(value.getContent());
            gen.writeFieldName("answer");
            gen.writeRawValue(value.getAnswer());
            gen.writeEndObject();
        }
    }

    private static final class QuestionDeserializer extends JsonDeserializer<Question> {
        @Override
        public Question deserialize(JsonParser p, DeserializationContext ctxt)
                throws IOException, JsonProcessingException {
            final JsonNode node = p.getCodec().readTree(p);
            final Question q = new Question();
            q.setId(node.get("id").asLong());
            q.setType(node.get("type").asText());
            q.setContent(node.get("content").toString());
            q.setAnswer(node.get("answer").toString());
            return q;
        }
    }
}
