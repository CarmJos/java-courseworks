package cc.carm.study.practicum.student.utils;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Validators {

    static final Pattern ID_PATTERN = Pattern.compile(
            "^[1-9]\\d{5}(19\\d{2}|20[0-2]\\d)(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])\\d{3}[0-9Xx]$"
    );
    static final int[] ID_WEIGHTS = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
    static final char[] ID_CODES = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' };

    static final Pattern PHONE_PATTERN = Pattern.compile(
            "^(?:\\+86)?1[3-9]\\d{9}$"
    );

    public static boolean validateID(@NotNull String id) {
        if (!ID_PATTERN.matcher(id).matches()) return false;
        int sum = IntStream.range(0, 17).map(i -> Character.getNumericValue(id.charAt(i)) * ID_WEIGHTS[i]).sum();
        return ID_CODES[sum % 11] == Character.toUpperCase(id.charAt(17));
    }

    public static boolean validatePhone(String phone) {
        Matcher matcher = PHONE_PATTERN.matcher(phone);
        return matcher.matches();
    }


}
