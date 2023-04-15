package com.guide;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConverterTest {
    @Test
    void test() {
        Converter.input("glob is I");
        Converter.input("prok is V");
        Converter.input("pish is X");
        Converter.input("tegj is L");

        String response = Converter.input("how much is pish tegj glob glob ?");
        assertEquals("pish tegj glob glob is 42", response);

        Converter.input("glob glob Silver is 34 Credits");
        response = Converter.input("how many Credits is glob prok Silver ?");
        assertEquals("glob prok Silver is 68 Credits", response);

        Converter.input("glob prok Gold is 57800 Credits");
        response = Converter.input("how many Credits is glob prok Gold ?");
        assertEquals("glob prok Gold is 57800 Credits", response);

        Converter.input("pish pish Iron is 3910 Credits");
        response = Converter.input("how many Credits is glob prok Iron ?");
        assertEquals("glob prok Iron is 782 Credits", response);

        response = Converter.input("how much wood could a woodchuck chuck if a woodchuck could chuck wood ?");
        assertEquals("I have no idea what you are talking about", response);
    }
}
