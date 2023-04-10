package com.guide;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.guide.RomanNumerals.getParts;
import static com.guide.RomanNumerals.getRomanNumber;
import static org.junit.jupiter.api.Assertions.*;

public class RomanNumeralTest {
    @Test
    void addNumeralsExamples() {
        assertEquals(2006, RomanNumerals.getInt("MMVI"));
        assertEquals(1944, RomanNumerals.getInt("MCMXLIV"));
    }

    @Test
    void addValuesWhenSmallSucceedsLarge() {
        assertEquals(6, RomanNumerals.getInt("VI"));
    }

    @Test
    void subtractValuesWhenSmallPrecedesLarge() {
        assertEquals(4, RomanNumerals.getInt("IV"));
    }

    @Test
    void dontRepeatIMoreThanThrice() {
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.getInt("IIII");
        });
    }

    @Test
    void dontRepeatXMoreThanThrice() {
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.getInt("XXXX");
        });
    }

    @Test
    void dontRepeatCMoreThanThrice() {
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.getInt("CCCC");
        });
    }

    @Test
    void dontRepeatMMoreThanThrice() {
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.getInt("MMMM");
        });
    }

    @Test
    void dontRepeatD() {
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.getInt("DD");
        });
    }

    @Test
    void dontRepeatL() {
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.getInt("LL");
        });
    }

    @Test
    void dontRepeatV() {
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.getInt("VV");
        });
    }

    @Test
    void ICanBeSubtractedFromVAndXOnly() {
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.getInt("IL");
        });
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.getInt("IC");
        });
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.getInt("ID");
        });
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.getInt("IM");
        });

        assertEquals(4, RomanNumerals.getInt("IV"));
        assertEquals(9, RomanNumerals.getInt("IX"));
    }

    @Test
    void XCanBeSubtractedFromLAndCOnly() {
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.getInt("XD");
        });
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.getInt("XM");
        });

        assertEquals(40, RomanNumerals.getInt("XL"));
        assertEquals(90, RomanNumerals.getInt("XC"));
    }

    @Test
    void CCanBeSubtractedFromDAndMOnly() {
        assertEquals(400, RomanNumerals.getInt("CD"));
        assertEquals(900, RomanNumerals.getInt("CM"));
    }

    @Test
    void doNotSubtractVLD() {
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.getInt("VX");
        });
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.getInt("LC");
        });
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.getInt("DM");
        });
    }

    @Test
    void SubtractOnlyOneSmallValueSymbolFromAnyLargeValueSymbol() {
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.getInt("IIV");
        });
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.getInt("IIX");
        });
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.getInt("XXL");
        });
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.getInt("XXC");
        });
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.getInt("CCD");
        });
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.getInt("CCM");
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
    void testGetRomanNumber_int() {
        assertEquals("MCMIII", getRomanNumber(1903));
        assertEquals("MMVI", getRomanNumber(2006));
        assertEquals("MCMXLIV", getRomanNumber(1944));
        assertEquals("MMMCMX", getRomanNumber(3910));
        assertEquals("MMMCMXCIX", getRomanNumber(3999));
    }

    @Test
    void testGetParts() {
        var ex1 = new Integer[] {1000, 900, 3};
        var ex2 = new Integer[] {2000, 6};
        var ex3 = new Integer[] {1000, 900, 40, 4};
        var ex4 = new Integer[] {3000, 900, 10};
        var ex5 = new Integer[] {3000, 900, 90, 9};

        assertArrayEquals(ex1, getParts(1903).toArray());
        assertArrayEquals(ex2, getParts(2006).toArray());
        assertArrayEquals(ex3, getParts(1944).toArray());
        assertArrayEquals(ex4, getParts(3910).toArray());
        assertArrayEquals(ex5, getParts(3999).toArray());
    }
}
