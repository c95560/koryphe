/*
 * Copyright 2017-2019 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.gov.gchq.koryphe.impl.function;

import org.junit.Test;

import uk.gov.gchq.koryphe.function.FunctionTest;
import uk.gov.gchq.koryphe.util.JsonSerialiser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class LengthTest extends FunctionTest {
    @Override
    protected Function getInstance() {
        return new Length();
    }

    @Override
    protected Class<? extends Function> getFunctionClass() {
        return Length.class;
    }

    @Override
    public void shouldJsonSerialiseAndDeserialise() throws IOException {
        // Given
        final Length function = new Length();

        // When
        final String json = JsonSerialiser.serialise(function);

        // Then
        JsonSerialiser.assertEquals(String.format("{%n" +
                "   \"class\" : \"uk.gov.gchq.koryphe.impl.function.Length\"%n" +
                "}"), json);

        // When 2
        final Length deserialised = JsonSerialiser.deserialise(json, Length.class);

        // Then
        assertNotNull(deserialised);
    }

    @Test
    public void shouldReturnZeroForNullInputValue() {
        // Given
        final Length function = new Length();
        final Object input = null;

        // When
        final Integer result = function.apply(input);

        // Then
        assertEquals(new Integer(0), result);
    }

    @Test
    public void shouldReturnLengthForStringInput() {
        // Given
        final Length function = new Length();
        final String input = "testString";

        // When
        final Integer result = function.apply(input);

        // Then
        assertEquals(new Integer(10), result);
    }
    
    @Test
    public void shouldReturnLengthForObjectArrayInput() {
        // Given
        final Length function = new Length();
        final Object[] input = new Object[5];
        
        // When
        final Integer result = function.apply(input);
        
        // Then
        assertEquals(new Integer(5), result);
    }
    
    @Test
    public void shouldReturnLengthForListInput() {
        // Given
        final Length function = new Length();
        final List<Object> input = new ArrayList<>();
        input.add(3);
        input.add(7.2);
        input.add("test");
        input.add("string");
        
        // When
        final Integer result = function.apply(input);
        
        // Then
        assertEquals(new Integer(4), result);
    }
    
    @Test
    public void shouldReturnLengthForSetInput() {
        // Given
        final Length function = new Length();
        final Set<Object> input = new HashSet<>();
        input.add(2.718);
        input.add(3.142);
        input.add("constants");
        
        // When
        final Integer result = function.apply(input);
        
        // Then
        assertEquals(new Integer(3), result);
    }
    
    @Test
    public void shouldReturnLengthForMapInput() {
        // Given
        final Length function = new Length();
        final Map<String, String> input = new HashMap<>();
        input.put("one", "two");
        input.put("three", "four");
        input.put("five", "six");
        input.put("seven", "eight");
        
        // When
        final Integer result = function.apply(input);
        
        // Then
        assertEquals(new Integer(4), result);
    }
    
    @Test
    public void shouldThrowExceptionForIncompatibleInputType() {
        // Given
        final Length function = new Length();
        final Concat input = new Concat();
        
        // When / Then
        try {
            function.apply(input);
            fail("Exception expected");
        } catch (final IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("Could not determine the size of the provided value"));
        }
    }
    
    @Test
    public void shouldCheckInputClass() {
        final Length function = new Length();

        assertTrue(function.isInputValid(String.class).isValid());
        assertTrue(function.isInputValid(Object[].class).isValid());
        assertTrue(function.isInputValid(Integer[].class).isValid());
        assertTrue(function.isInputValid(Collection.class).isValid());
        assertTrue(function.isInputValid(List.class).isValid());
        assertTrue(function.isInputValid(Map.class).isValid());
        assertTrue(function.isInputValid(HashMap.class).isValid());

        assertFalse(function.isInputValid(String.class, HashMap.class).isValid());
        assertFalse(function.isInputValid(Double.class).isValid());
        assertFalse(function.isInputValid(Integer.class, Integer.class).isValid());
    }
}
