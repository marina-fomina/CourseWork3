package ru.fomina.socks.model;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;
import nonapi.io.github.classgraph.json.JSONUtils;

import java.io.IOException;
import java.util.HashMap;

public class MyKeyDeserializer extends KeyDeserializer {

    @Override
    public Object deserializeKey(String s, DeserializationContext deserializationContext) throws IOException {

        return null;
    }

//    private HashMap<Socks, Integer> socksMap = new HashMap<>();
//
//    public MyKeyDeserializer() {
//        this(null);
//    }
//
//    public MyKeyDeserializer(Class<?> vc) {
//        super(vc);
//    }
//
//    @Override
//    public HashMap<Socks, Integer> deserialize(JsonParser jp, DeserializationContext deserializationContext)
//            throws IOException, JacksonException {
//        JsonNode node = jp.getCodec().readTree(jp);
//        Colour colour = Colour.valueOf(node.get("colour").asText());
//        Size size = Size.valueOf(node.get("size").asText());
//        int cottonPart = (Integer) ((IntNode) node.get("cottonPart")).numberValue();
//        int quantity = (Integer) ((IntNode) node.get("quantity")).numberValue();
//        socksMap.put(new Socks(colour, size, cottonPart), quantity);
//        return socksMap;
//    }
}
