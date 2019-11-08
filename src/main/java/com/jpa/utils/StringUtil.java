package com.jpa.utils;

import ognl.Ognl;
import ognl.OgnlException;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public StringUtil() {
    }

    public static String randomUrl(String url) {
        if (!isValidStr(url)) {
            return url;
        } else {
            if (url.indexOf("?") == -1) {
                url = url + "?";
            }

            url = url + "&r=" + (new Random()).nextLong();
            return url;
        }
    }

    public static String encryptMD5(String str) {
        if (!isValidStr(str)) {
            return str;
        } else {
            MessageDigest md = null;

            try {
                md = MessageDigest.getInstance("MD5");
            } catch (Exception var6) {
                System.out.println("encrypt failed for NoSuchAlgorithmException 'MD5'");
                return str;
            }

            byte[] buffer = md.digest(str.getBytes());
            StringBuffer returnBuffer = new StringBuffer();
            int pos = 0;

            for(int len = buffer.length; pos < len; ++pos) {
                returnBuffer.append(hexToAscii(buffer[pos] >>> 4 & 15)).append(hexToAscii(buffer[pos] & 15));
            }

            return returnBuffer.toString();
        }
    }

    private static char hexToAscii(int h) {
        if (h >= 10 && h <= 15) {
            return (char)(97 + (h - 10));
        } else if (h >= 0 && h <= 9) {
            return (char)(48 + h);
        } else {
            throw new Error("hex to ascii failed");
        }
    }

    public static String dealNull(String str) {
        return str == null ? "" : str;
    }

    public static String dealNull(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    public static boolean isValidStr(String str) {
        return str != null && !str.equals("");
    }

    public static String parseClassSimpleName(String className) {
        int pos = className.indexOf(".");
        if (pos == -1) {
            return className;
        } else {
            int temp;
            while((temp = className.indexOf(".", pos + 1)) != -1) {
                pos = temp;
            }

            return className.substring(pos + 1, className.length());
        }
    }

    public static String parseSimpleFileName(String fileName) {
        String pattern = "[^\\\\|.]+\\..+";
        Pattern p = Pattern.compile(pattern);
        Matcher matcher = p.matcher(fileName);
        if (matcher.find()) {
            String one = matcher.group();
            return one;
        } else {
            throw new RuntimeException("Tools.parseSimpleFileName()在解析完整路径名（" + fileName + "）时出错!");
        }
    }

    public static String parseFieldNameFromMethod(String methodName) {
        String rawField = "";
        if (methodName != null && !methodName.equals("")) {
            if (methodName.startsWith("get")) {
                rawField = methodName.substring(3);
            } else if (methodName.startsWith("is")) {
                rawField = methodName.substring(2);
            } else {
                if (!methodName.startsWith("set")) {
                    return methodName;
                }

                rawField = methodName.substring(3);
            }

            if (rawField.equals("")) {
                return null;
            } else {
                StringBuffer sb = new StringBuffer();
                sb.append(rawField.substring(0, 1).toLowerCase());
                sb.append(rawField.substring(1));
                return sb.toString();
            }
        } else {
            return null;
        }
    }

    public static String transList2String(List list, String property, String split) {
        if (list != null && isValidStr(property)) {
            ArrayList result = new ArrayList();
            Iterator iterator = list.iterator();

            while(iterator.hasNext()) {
                Object source = iterator.next();
                if (source != null) {
                    try {
                        Object value = Ognl.getValue(property, source);
                        if (value != null) {
                            result.add(value.toString());
                        }
                    } catch (OgnlException var7) {
                        var7.printStackTrace();
                    }
                }
            }

            return transList2String(result, split);
        } else {
            return "";
        }
    }

    public static String transList2String(List strList, String split) {
        try {
            if (strList.size() <= 0) {
                return "";
            } else {
                String strReturn = "";

                for(int i = 0; i < strList.size(); ++i) {
                    strReturn = strReturn + split + strList.get(i);
                }

                return strReturn.substring(split.length());
            }
        } catch (Exception var4) {
            return "";
        }
    }

    public static String transStringAry2String(String[] strAry, String split) {
        if (strAry != null && strAry.length != 0) {
            try {
                String strReturn = "";

                for(int i = 0; i < strAry.length; ++i) {
                    strReturn = strReturn + split + strAry[i];
                }

                return strReturn.substring(split.length());
            } catch (Exception var4) {
                return "";
            }
        } else {
            return "";
        }
    }

    public static String[] transList2StringArray(List alStr) {
        try {
            return alStr != null && alStr.size() != 0 ? (String[])((String[])alStr.toArray(new String[0])) : null;
        } catch (Exception var2) {
            //Log.exp("StringProcess--ArrayList2StringArray--", var2);
            return null;
        }
    }

    public static List transString2List(String str, String split) {
        try {
            ArrayList al = new ArrayList();
            if (str != null && !str.equals("")) {
                if (split != null && !"".equals(split)) {
                    str = str + split;
                    int begin = 0;

                    for(int end = str.indexOf(split, begin); end >= 0; end = str.indexOf(split, begin)) {
                        al.add(str.substring(begin, end));
                        begin = end + split.length();
                    }

                    return al;
                } else {
                    al.add(str);
                    return al;
                }
            } else {
                return al;
            }
        } catch (Exception var5) {
            //Log.exp("StringProcess--String2ArrayList--", var5);
            return null;
        }
    }

    public static String[] transString2StringAry(String str, String split) {
        try {
            return transList2StringArray(transString2List(str, split));
        } catch (Exception var3) {
            //Log.exp("StringProcess--String2StringArray--", var3);
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(transNumber2String(11111, 4));
    }

    public static String replaceFieldByValue(String str, Object value, String pre, String post) {
        if (!hasEnclosedField(str, pre, post)) {
            return str;
        } else {
            int prePos = str.indexOf(pre);
            int postPos = str.indexOf(post, prePos);
            return str.substring(0, prePos) + value.toString() + str.substring(postPos + post.length(), str.length());
        }
    }

    public static String transNumber2String(long num, int length) {
        if (num >= 0L && length >= 0) {
            String numStr = num + "";
            return transNumber2String(numStr, length);
        } else {
            return num + "";
        }
    }

    public static String transNumber2String(int num, int length) {
        if (num >= 0 && length >= 0) {
            String numStr = num + "";
            return transNumber2String(numStr, length);
        } else {
            return num + "";
        }
    }

    public static String transNumber2String(String num, int length) {
        if (num != null && length >= 0) {
            int sub = length - num.length();
            if (sub > 0) {
                for(int i = 0; i < sub; ++i) {
                    num = "0" + num;
                }
            }

            return num;
        } else {
            return null;
        }
    }

    public static String getEnclosedField(String str, String pre, String post) {
        if (!hasEnclosedField(str, pre, post)) {
            return str;
        } else {
            int prePos = str.indexOf(pre);
            int postPos = str.indexOf(post, prePos);
            return str.substring(prePos + pre.length(), postPos);
        }
    }

    public static boolean hasEnclosedField(String str, String pre, String post) {
        int prePos = str.indexOf(pre);
        int postPos = str.indexOf(post, prePos);
        return prePos != -1 && postPos != -1 && postPos >= prePos + pre.length();
    }

    public static String fromEncodedUnicode(String codedStr) {
        return !isValidStr(codedStr) ? codedStr : fromEncodedUnicode(codedStr.toCharArray(), 0, codedStr.length());
    }

    public static String fromEncodedUnicode(char[] in, int off, int len) {
        char[] out = new char[len];
        int outLen = 0;
        int end = off + len;

        while(true) {
            while(true) {
                while(off < end) {
                    char aChar = in[off++];
                    if (aChar == '\\') {
                        aChar = in[off++];
                        if (aChar == 'u') {
                            int value = 0;

                            for(int i = 0; i < 4; ++i) {
                                aChar = in[off++];
                                switch(aChar) {
                                    case '0':
                                    case '1':
                                    case '2':
                                    case '3':
                                    case '4':
                                    case '5':
                                    case '6':
                                    case '7':
                                    case '8':
                                    case '9':
                                        value = (value << 4) + aChar - 48;
                                        break;
                                    case ':':
                                    case ';':
                                    case '<':
                                    case '=':
                                    case '>':
                                    case '?':
                                    case '@':
                                    case 'G':
                                    case 'H':
                                    case 'I':
                                    case 'J':
                                    case 'K':
                                    case 'L':
                                    case 'M':
                                    case 'N':
                                    case 'O':
                                    case 'P':
                                    case 'Q':
                                    case 'R':
                                    case 'S':
                                    case 'T':
                                    case 'U':
                                    case 'V':
                                    case 'W':
                                    case 'X':
                                    case 'Y':
                                    case 'Z':
                                    case '[':
                                    case '\\':
                                    case ']':
                                    case '^':
                                    case '_':
                                    case '`':
                                    default:
                                        throw new IllegalArgumentException("Malformed \\uxxxx encoding.");
                                    case 'A':
                                    case 'B':
                                    case 'C':
                                    case 'D':
                                    case 'E':
                                    case 'F':
                                        value = (value << 4) + 10 + aChar - 65;
                                        break;
                                    case 'a':
                                    case 'b':
                                    case 'c':
                                    case 'd':
                                    case 'e':
                                    case 'f':
                                        value = (value << 4) + 10 + aChar - 97;
                                }
                            }

                            out[outLen++] = (char)value;
                        } else {
                            if (aChar == 't') {
                                aChar = '\t';
                            } else if (aChar == 'r') {
                                aChar = '\r';
                            } else if (aChar == 'n') {
                                aChar = '\n';
                            } else if (aChar == 'f') {
                                aChar = '\f';
                            }

                            out[outLen++] = aChar;
                        }
                    } else {
                        out[outLen++] = aChar;
                    }
                }

                return new String(out, 0, outLen);
            }
        }
    }
}
