package xyz.dahoba;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Created by siritas_s on 3/31/2017 AD.
 */
@Slf4j
@Component
public class RomanCalculator {
    private String sourceString;

    private String[] romanLetters = {"I", "V", "X", "L", "C", "D", "M"};
    private int[] romanLettersValue = {1, 5, 10, 50, 100, 500, 1000};
    private String[] subtractLetters = {"IV", "IX", "XL", "XC", "CD", "CM"};
    private String[] allRomanNumberLetters = {"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M"};
    private int[] allRomanNumberLettersValue = {1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};
    private int[] subtractValue = {4, 9, 40, 90, 400, 900};
    private final int REPEAT_LIMIT = 3;
    private final int SUBTRACT_LIMIT = 2;

    // Rules
    // 'I', 'X', 'C', and 'M' can be repeated at most 3 times in a row
    // "D", "L", and "V" can never be repeated.
    // "I" can be subtracted from "V" and "X" only
    // "X" can be subtracted from "L" and "C" only
    // "C" can be subtracted from "D" and "M" only
    // "V", "L", and "D" can never be subtracted.

    public void calculate(String sourceString) {
        this.sourceString = sourceString.toUpperCase();

        if (null == sourceString || isInvalidLetters(this.sourceString)) {
            throw new RuntimeException("Invalid input");
        }
        String[] lettersArray;
        int total = 0;
        log.debug(" ");
        log.debug(sourceString + " > ");
        if (this.sourceString.contains("+")) {
            lettersArray = this.sourceString.split("\\+");

            for (String letterItem : lettersArray) {
                total += toArabic(letterItem);
            }
        } else {
            //not yet implement.
            System.out.println("use + sign to add the roman numeral");
        }
        log.debug("total:: " + total);
        System.out.print(toRoman(total));
        System.out.println("");
    }


    private int toArabic(String letters) {
        int arabicNumber = 0;
        int letterIndex = 0;
        String letter;
        int letterValueIndex;

        while (letterIndex < letters.length()) {
            letter = letters.substring(letterIndex, letterIndex + 1);
            log.debug("letter: " + letter);
            log.debug("letterIndex: " + letterIndex);
            if (isThreeLetterRepeat(letter, letters)) {
                letterValueIndex = Arrays.asList(romanLetters).indexOf(letter);
                arabicNumber += romanLettersValue[letterValueIndex] * REPEAT_LIMIT;
                letterIndex += REPEAT_LIMIT;
            } else if (isSubtraction(letterIndex, letters)) {
                if (letterIndex + 2 > letters.length()) {
                    break;
                }
                letter = letters.substring(letterIndex, letterIndex + SUBTRACT_LIMIT);
                letterValueIndex = Arrays.asList(subtractLetters).indexOf(letter);
                arabicNumber += subtractValue[letterValueIndex];
                letterIndex += SUBTRACT_LIMIT;
            } else {
                letterValueIndex = Arrays.asList(romanLetters).indexOf(letter);
                arabicNumber += romanLettersValue[letterValueIndex];
                letterIndex++;
            }
            log.debug("sub total: " + arabicNumber);
        }
        return arabicNumber;
    }

    private String toRoman(int number) {
        String letters = "";

        for (int i = (allRomanNumberLettersValue.length - 1); i > -1; i--) {
            while (number >= allRomanNumberLettersValue[i]) {
                letters = letters.concat(allRomanNumberLetters[i]);
                number = number - allRomanNumberLettersValue[i];
            }
        }
        return letters;
    }

    private boolean isThreeLetterRepeat(String letter, String letters) {
        boolean result = letters.matches(letter + "{" + REPEAT_LIMIT + "}");
        return result;
    }

    private boolean isSubtraction(int letterIndex, String letters) {
        if (null == letters || "".equals(letters) || letterIndex + SUBTRACT_LIMIT > letters.length()) {
            return false;
        }
        String subtractString = letters.substring(letterIndex, letterIndex + SUBTRACT_LIMIT); //i.e.: IV, IX
        return Arrays.asList(subtractLetters).contains(subtractString);
    }

    private boolean isInvalidLetters(String letters) {
        log.debug(" check letters: " + letters);
        boolean check = false;
        for (int i = 0; i < letters.length(); i++) {
            String letter = letters.substring(i, i + 1);
            log.debug(" contain ?: " + letter);
            if ("+".equals(letter)) {
                continue;
            }
            if (Arrays.asList(romanLetters).indexOf(letter) == -1) {
                log.debug("!! " + letter);
                check = true;
            }
        }
        return check;
    }
}
