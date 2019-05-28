package lotto.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lotto.exceptions.IllegalInputFormatException;

public class InputParser {
    public static List<LottoNumber> parse(String input) {
        if (!input.matches("([^,]+)(, [^,]+)*")) {
            throw new IllegalInputFormatException("입력 형식이 올바르지 않습니다.");
        }
        String[] tokens = input.split(", ");
        List<LottoNumber> result = new ArrayList<>();
        for (String token : tokens) {
            result.add(LottoNumber.of(Integer.parseInt(token)));
        }
        Collections.sort(result);
        return result;
    }
}
