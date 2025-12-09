package cc.carm.study.practicum.student.utils;

import java.util.regex.Pattern;

public class Validators {

    static final Pattern ID_PATTERN = Pattern.compile(
            "^[1-9]\\d{5}(19\\d{2}|20[0-2]\\d)(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])\\d{3}[0-9Xx]$"
    );
    static final int[] ID_WEIGHTS = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
    static final char[] ID_CODES = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};

    static final Pattern PHONE_PATTERN = Pattern.compile(
            "^(?:\\+86)?1[3-9]\\d{9}$"
    );

    /**
     * 快速验证身份证号码是否合法
     *
     * @param id 身份证号码
     * @return 是否合法
     */
    public static boolean validateID(String id) {
        if (id == null || !ID_PATTERN.matcher(id).matches()) return false;
        int sum = 0;
        for (int i = 0; i < 17; i++) { // 除去最后一位校验码，计算前17位的加权和
            int i1 = Character.getNumericValue(id.charAt(i)) * ID_WEIGHTS[i];
            sum += i1;
        }
        return ID_CODES[sum % 11] == Character.toUpperCase(id.charAt(17)); // 校验码匹配
    }

    /**
     * 快速验证国内手机号是否合法
     *
     * @param phone 手机号码
     * @return 是否合法
     */
    public static boolean validatePhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }


}
