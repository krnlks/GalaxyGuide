package com.guide;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConverterTest {
    @Test
    void test() {
        Converter c = new Converter();
        c.generateResponse("glob is I");
        c.generateResponse("prok is V");
        c.generateResponse("pish is X");
        c.generateResponse("tegj is L");

        String response = c.generateResponse("how much is pish tegj glob glob ?");
        assertEquals("pish tegj glob glob is 42", response);

        c.generateResponse("glob glob Silver is 34 Credits");
        response = c.generateResponse("how many Credits is glob prok Silver ?");
        assertEquals("glob prok Silver is 68 Credits", response);

        c.generateResponse("glob prok Gold is 57800 Credits");
        response = c.generateResponse("how many Credits is glob prok Gold ?");
        assertEquals("glob prok Gold is 57800 Credits", response);

        c.generateResponse("pish pish Iron is 3910 Credits");
        response = c.generateResponse("how many Credits is glob prok Iron ?");
        assertEquals("glob prok Iron is 782 Credits", response);

        response = c.generateResponse("how much wood could a woodchuck chuck if a woodchuck could chuck wood ?");
        assertEquals("I have no idea what you are talking about", response);
    }
}
