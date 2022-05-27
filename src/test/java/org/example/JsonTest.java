package org.example;

import com.google.gson.Gson;
import net.javacrumbs.jsonunit.JsonAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static net.javacrumbs.jsonunit.core.Option.IGNORING_ARRAY_ORDER;

public class JsonTest {

    @Test
    @DisplayName("Json body test")
    void testMessageBody() throws IOException {

        String expected = getResourceAsString("expected.json");

        Message actually = Message.builder()
                .author("Mike")
                .text("text")
                .createdAt(new Date())
                .build();

        JsonAssert.assertJsonEquals(
                expected,
                actually,
                JsonAssert.when(IGNORING_ARRAY_ORDER)
        );
    }

    public void printJson(Object o) {
        System.out.println(new Gson().toJson(o));
    }

    public String getResourceAsString(String resource) throws IOException {
        InputStream stream = getClass().getResourceAsStream(resource);
        assert stream != null;
        byte[] bytes = stream.readAllBytes();
        return new String(bytes, StandardCharsets.UTF_8);
    }
}