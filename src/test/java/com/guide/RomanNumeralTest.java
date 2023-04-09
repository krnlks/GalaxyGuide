package com.guide;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RomanNumeralTest {
    @Test
    void addNumeralsExamples() {
        assertEquals(2006, RomanNumerals.toInt("MMVI"));
        assertEquals(1944, RomanNumerals.toInt("MCMXLIV"));
    }

    @Test
    void addValuesWhenSmallSucceedsLarge() {
        assertEquals(6, RomanNumerals.toInt("VI"));
    }

    @Test
    void subtractValuesWhenSmallPrecedesLarge() {
        assertEquals(4, RomanNumerals.toInt("IV"));
    }

    @Test
    void dontRepeatIMoreThanThrice() {
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.toInt("IIII");
        });
    }

    @Test
    void dontRepeatXMoreThanThrice() {
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.toInt("XXXX");
        });
    }

    @Test
    void dontRepeatCMoreThanThrice() {
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.toInt("CCCC");
        });
    }

    @Test
    void dontRepeatMMoreThanThrice() {
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.toInt("MMMM");
        });
    }

    @Test
    void dontRepeatD() {
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.toInt("DD");
        });
    }

    @Test
    void dontRepeatL() {
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.toInt("LL");
        });
    }

    @Test
    void dontRepeatV() {
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.toInt("VV");
        });
    }

    @Test
    void ICanBeSubtractedFromVAndXOnly() {
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.toInt("IL");
        });
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.toInt("IC");
        });
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.toInt("ID");
        });
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.toInt("IM");
        });

        assertEquals(4, RomanNumerals.toInt("IV"));
        assertEquals(9, RomanNumerals.toInt("IX"));
    }

    @Test
    void XCanBeSubtractedFromLAndCOnly() {
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.toInt("XD");
        });
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.toInt("XM");
        });

        assertEquals(40, RomanNumerals.toInt("XL"));
        assertEquals(90, RomanNumerals.toInt("XC"));
    }

    @Test
    void CCanBeSubtractedFromDAndMOnly() {
        assertEquals(400, RomanNumerals.toInt("CD"));
        assertEquals(900, RomanNumerals.toInt("CM"));
    }

    @Test
    void doNotSubtractVLD() {
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.toInt("VX");
        });
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.toInt("LC");
        });
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.toInt("DM");
        });
    }

    @Test
    void SubtractOnlyOneSmallValueSymbolFromAnyLargeValueSymbol() {
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.toInt("IIV");
        });
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.toInt("IIX");
        });
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.toInt("XXL");
        });
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.toInt("XXC");
        });
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.toInt("CCD");
        });
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.toInt("CCM");
        });
    }

    @Test
    public void testExtractSummands() {
        String[] testLiterals = new String[]{
                "MCMXLIV"
        };

        List<char[]> summands = RomanNumerals.extractSummands(testLiterals[0]);

        List<char[]> shouldBe = new ArrayList<>();
        shouldBe.add("M".toCharArray());
        shouldBe.add("CM".toCharArray());
        shouldBe.add("XL".toCharArray());
        shouldBe.add("IV".toCharArray());

        assertArrayEquals(shouldBe.toArray(), summands.toArray());
    }
}
