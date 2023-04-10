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

    @Test
    void testGetNumeralExamples() {
        assertEquals("MCMIII", RomanNumerals.getNumeral(1903));
        assertEquals("MMVI", RomanNumerals.getNumeral(2006));
        assertEquals("MCMXLIV", RomanNumerals.getNumeral(1944));
        assertEquals("MMMCMX", RomanNumerals.getNumeral(3910));
        assertEquals("MMMCMXCIX", RomanNumerals.getNumeral(3999));
    }

    @Test
    void testGetParts() {
        var ex1 = new Integer[] {1000, 900, 3};
        var ex2 = new Integer[] {2000, 6};
        var ex3 = new Integer[] {1000, 900, 40, 4};
        var ex4 = new Integer[] {3000, 900, 10};
        var ex5 = new Integer[] {3000, 900, 90, 9};

        assertArrayEquals(ex1, RomanNumerals.getParts(1903).toArray());
        assertArrayEquals(ex2, RomanNumerals.getParts(2006).toArray());
        assertArrayEquals(ex3, RomanNumerals.getParts(1944).toArray());
        assertArrayEquals(ex4, RomanNumerals.getParts(3910).toArray());
        assertArrayEquals(ex5, RomanNumerals.getParts(3999).toArray());
    }
}
