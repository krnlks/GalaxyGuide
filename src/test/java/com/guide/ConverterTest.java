package com.guide;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConverterTest {
    @Test
    void test() {
        Converter c = new Converter();
        c.input("glob is I");
        c.input("prok is V");
        c.input("pish is X");
        c.input("tegj is L");

        String response = c.input("how much is pish tegj glob glob ?");
        assertEquals("pish tegj glob glob is 42", response);

        c.input("glob glob Silver is 34 Credits");
        response = c.input("how many Credits is glob prok Silver ?");
        assertEquals("glob prok Silver is 68 Credits", response);

        c.input("glob prok Gold is 57800 Credits");
        response = c.input("how many Credits is glob prok Gold ?");
        assertEquals("glob prok Gold is 57800 Credits", response);

        c.input("pish pish Iron is 3910 Credits");
        response = c.input("how many Credits is glob prok Iron ?");
        assertEquals("glob prok Iron is 782 Credits", response);

        response = c.input("how much wood could a woodchuck chuck if a woodchuck could chuck wood ?");
        assertEquals("I have no idea what you are talking about", response);
    }
}
