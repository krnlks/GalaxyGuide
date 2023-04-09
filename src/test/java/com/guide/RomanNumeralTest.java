package com.guide;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RomanNumeralTest {
    @Test
    void addNumeralsExample1() {
        assertEquals(RomanNumerals.toInt("MMVI"), 2006);
    }

    @Test
    void addNumeralsExample2() {
        assertEquals(RomanNumerals.toInt("MCMXLIV"), 1944);
    }

    @Test
    void addValuesWhenSmallSucceedsLarge() {
        assertEquals(RomanNumerals.toInt("VI"), 6);
    }

    @Test
    void subtractValuesWhenSmallPrecedesLarge() {
        assertEquals(RomanNumerals.toInt("IV"), 4);
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

        assertEquals(RomanNumerals.toInt("IV"), 4);
        assertEquals(RomanNumerals.toInt("IX"), 9);
    }

    @Test
    void XCanBeSubtractedFromLAndCOnly() {
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.toInt("XD");
        });
        assertThrows(NumberFormatException.class, () -> {
            RomanNumerals.toInt("XM");
        });

        assertEquals(RomanNumerals.toInt("XL"), 40);
        assertEquals(RomanNumerals.toInt("XC"), 90);
    }

    @Test
    void CCanBeSubtractedFromDAndMOnly() {
        assertEquals(RomanNumerals.toInt("CD"), 400);
        assertEquals(RomanNumerals.toInt("CM"), 900);
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

        assertEquals(shouldBe.toArray(), summands.toArray());
    }
}
