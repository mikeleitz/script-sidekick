/*
 *  Copyright (c) 2020, Michael Leitz
 *  <p/>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p/>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p/>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.mikeleitz.sidekick.bash.domain;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.IntNode;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BashValidationDeserializer extends StdDeserializer<BashValidation> {
    private static final String ARG_KEY_NAME = "key";
    private static final String ARG_KEY_VALUE = "value";

    private BashValidationFactory bashValidationFactory = new BashValidationFactory();

    public BashValidationDeserializer() {
        this(null);
    }

    public BashValidationDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public BashValidation deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        BashValidation returnValue = null;

        JsonNode node = jp.getCodec().readTree(jp);

        Integer id = (Integer) ((IntNode) node.get("id")).numberValue();
        String name = node.get("name").asText();
        List<Pair<String, String>> args = Collections.emptyList();

        ArrayNode argsArrayNode = (ArrayNode) node.get("args");
        if (argsArrayNode != null && !argsArrayNode.isNull()) {
            // Stream the nodes and create a list of key/value arg pairs.
            args = IntStream.range(0, argsArrayNode.size())
                    .mapToObj(argsArrayNode::get)
                    .map(n -> new ImmutablePair<>(n.get(ARG_KEY_NAME).asText(),
                            n.get(ARG_KEY_VALUE).asText()))
                    .collect(Collectors.toList());
        }

        returnValue = bashValidationFactory.createBashValidation(id, name, args);

        return returnValue;
    }
}
