package com.etherscan.script.utils;

import java.io.UnsupportedEncodingException;

// https://apps.timwhitlock.info/emoji/tables/unicode
public class Emoji
{
    public static String book()
    {
        return toSting(new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x93, (byte) 0x9D});
    }

    public static String handToRight()
    {
        return toSting(new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x91, (byte) 0x89});
    }

    public static String handDown()
    {
        return toSting(new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x91, (byte) 0x87});
    }

    public static String birthdayCake()
    {
        return toSting(new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x8E, (byte) 0x82});
    }

    public static String store()
    {
        return toSting(new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x8F, (byte) 0xAC});
    }

    public static String reload()
    {
        return toSting(new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x94, (byte) 0x84});
    }

    public static String checkBoxOk()
    {
        return toSting(new byte[]{(byte) 0xE2, (byte) 0x9C, (byte) 0x85});
    }

    public static String blackTelephone()
    {
        return toSting(new byte[]{(byte) 0xE2, (byte) 0x98, (byte) 0x8E});
    }

    public static String heavyExclamationMarkSymbol()
    {
        return toSting(new byte[]{(byte) 0xE2, (byte) 0x9D, (byte) 0x97});
    }

    public static String ambulance()
    {
        return toSting(new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x9A,  (byte) 0x91});
    }

    public static String id()
    {
        return toSting(new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x86,  (byte) 0x94});
    }

    public static String packageBox()
    {
        return toSting(new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x93,  (byte) 0xA6});
    }

    public static String whiteHeavyCheckMark()
    {
        return toSting(new byte[]{(byte) 0xE2, (byte) 0x9C, (byte) 0x85});
    }

    public static String heavyCheckMark()
    {
        return toSting(new byte[]{(byte) 0xE2, (byte) 0x9C, (byte) 0x94});
    }

    public static String creditCard()
    {
        return toSting(new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x92, (byte) 0xB3});
    }

    public static String bank()
    {
        return toSting(new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x8F, (byte) 0xA6});
    }

    public static String redBook()
    {
        return toSting(new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x93, (byte) 0x95});
    }

    public static String greenBook()
    {
        return toSting(new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x93, (byte) 0x97});
    }

    public static String orangeBook()
    {
        return toSting(new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x93, (byte) 0x99});
    }

    public static String blueBook()
    {
        return toSting(new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x93, (byte) 0x98});
    }

    public static String assistant()
    {
        return toSting(new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x91, (byte) 0xA9});
    }

    public static String question()
    {
        return toSting(new byte[]{(byte) 0xE2, (byte) 0x9D, (byte) 0x93});
    }

    public static String clipboard()
    {
        return toSting(new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x93, (byte) 0x8B});
    }

    public static String star()
    {
        return toSting(new byte[]{(byte) 0xE2, (byte) 0xAD, (byte) 0x90});
    }

    public static String banknote()
    {
        return toSting(new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x92, (byte) 0xB5});
    }

    public static String stone()
    {
        return toSting(new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x92, (byte) 0x8E});
    }

    public static String calendar()
    {
        return toSting(new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x93, (byte) 0x85});
    }

    public static String memo()
    {
        return toSting(new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x93, (byte) 0x9D});
    }

    public static String grinningFace()
    {
        return toSting(new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x80});
    }

    public static String telephone()
    {
        return toSting(new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x93, (byte) 0x9E});
    }

    public static String crossMark()
    {
        return toSting(new byte[]{(byte) 0xE2, (byte) 0x9D, (byte) 0x8C});
    }

    public static String mobilePhoneWithRightwardsArrowAtLeft()
    {
        return toSting(new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x93, (byte) 0xB2});
    }

    public static String fire()
    {
        return toSting(new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x94, (byte) 0xA5});
    }

    public static String barChart()
    {
        return toSting(new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x93, (byte) 0x8A});
    }

    public static String moneyBag()
    {
        return toSting(new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x92, (byte) 0xB0});
    }

    public static String blackRightPointingDoubleTriangle()
    {
        return toSting(new byte[]{(byte) 0xE2, (byte) 0x8F, (byte) 0xA9});
    }

    private static String toSting(byte[] bytes)
    {
        String s = "";
        try
        {
            s = new String(bytes, "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return s;
    }
}
