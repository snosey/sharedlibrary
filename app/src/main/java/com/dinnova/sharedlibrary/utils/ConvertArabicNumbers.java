package com.dinnova.sharedlibrary.utils;

public class ConvertArabicNumbers {
    public static String convert(String number) {
        return number
                .replace("١", "1").replace("٠", "0")
                .replace("٣", "3").replace("٢", "2")
                .replace("٥", "5").replace("٤", "4")
                .replace("٧", "7").replace("٦", "6")
                .replace("٩", "9").replace("٨", "8")
                ;
    }
}
