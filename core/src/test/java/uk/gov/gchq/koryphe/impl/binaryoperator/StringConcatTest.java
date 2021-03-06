package uk.gov.gchq.koryphe.impl.binaryoperator;

import org.junit.Before;
import org.junit.Test;

import uk.gov.gchq.koryphe.binaryoperator.BinaryOperatorTest;
import uk.gov.gchq.koryphe.util.JsonSerialiser;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StringConcatTest extends BinaryOperatorTest {
    private String state;

    @Before
    public void before() {
        state = null;
    }

    @Test
    public void shouldConcatStringsTogether() {
        // Given
        final StringConcat function = new StringConcat();
        function.setSeparator(";");

        // When
        state = function.apply(state, "1");
        state = function.apply(state, "2");
        state = function.apply(state, null);

        // Then
        assertEquals("1;2", state);
    }

    @Test
    public void shouldJsonSerialiseAndDeserialise() throws IOException {
        // Given
        final StringConcat function = new StringConcat();

        // When 1
        final String json = JsonSerialiser.serialise(function);

        // Then 1
        JsonSerialiser.assertEquals(String.format("{%n" +
                "  \"class\" : \"uk.gov.gchq.koryphe.impl.binaryoperator.StringConcat\",%n" +
                "  \"separator\" : \",\"%n" +
                "}"), json);

        // When 2
        final StringConcat deserialisedAggregator = JsonSerialiser.deserialise(json, getFunctionClass());

        // Then 2
        assertNotNull(deserialisedAggregator);
    }

    @Override
    protected StringConcat getInstance() {
        return new StringConcat();
    }

    @Override
    protected Class<StringConcat> getFunctionClass() {
        return StringConcat.class;
    }
}
