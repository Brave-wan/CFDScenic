package com.demo.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作者：sonnerly on 2016/8/21 0021 21:21
 */
public class VerifyPhoneNumber {
    public static boolean isMobileNO(String mobiles) {
        //Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|17[678]|(18[0,5-9]))\\d{8}$");
        Pattern p = Pattern.compile("^1\\d{10}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

}
