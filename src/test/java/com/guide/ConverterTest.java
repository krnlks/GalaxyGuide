package com.guide;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConverterTest {
    @Test
    void testGenerateNumberConversionQueryResponse() {
        Converter c = new Converter();
        defineTerms(c);

        String response = c.submitQuery("how much is pish tegj glob glob ?");
        assertEquals("pish tegj glob glob is 42", response);
    }

    @Test
    void testGenerateCreditsPerGoodsQueryResponse() {
        Converter c = new Converter();
        defineTerms(c);
        assignCreditsToGood(c);

        String response = c.submitQuery("how many Credits is glob prok Silver ?");
        assertEquals("glob prok Silver is 68 Credits", response);

        response = c.submitQuery("how many Credits is glob prok Gold ?");
        assertEquals("glob prok Gold is 57800 Credits", response);

        response = c.submitQuery("how many Credits is glob prok Iron ?");
        assertEquals("glob prok Iron is 782 Credits", response);
    }

    @Test
    void testInvalidQueries() {
        Converter c = new Converter();
        defineTerms(c);

        String response = c.submitQuery("how much wood could a woodchuck chuck if a woodchuck could chuck wood ?");
        assertEquals("I have no idea what you are talking about", response);
    }

    private static void defineTerms(Converter c) {
        c.submitQuery("glob is I");
        c.submitQuery("prok is V");
        c.submitQuery("pish is X");
        c.submitQuery("tegj is L");
    }

    private static void assignCreditsToGood(Converter c) {
        c.submitQuery("glob glob Silver is 34 Credits");
        c.submitQuery("glob prok Gold is 57800 Credits");
        c.submitQuery("pish pish Iron is 3910 Credits");
    }
}
