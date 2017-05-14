package com.quickindex;

import android.text.TextUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

/**
 * Created by tony on 2017/5/2.
 */

public class PinYinHelper {
    public static String getPinYin(String text) {
        if (TextUtils.isEmpty(text)) {
            return null;
        }

        char[] charArray = text.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        HanyuPinyinOutputFormat formater = new HanyuPinyinOutputFormat();
        formater.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        formater.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

        for (int index = 0; index < charArray.length; index++) {
            char letter = charArray[index];
            if (Character.isWhitespace(letter))
                continue;

            if (letter <= 127) {
                stringBuilder.append(letter);
            } else {
                //可能是汉字
                try {
                    String[] pinyingArray = PinyinHelper.toHanyuPinyinStringArray(letter, formater);
                    if (pinyingArray != null) {
                        stringBuilder.append(pinyingArray[0]);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return stringBuilder.toString();
    }
}
