public class stringComparing {

    public static void main(String[] args) {
        String str1 = "https://www.onetwotrip.com/en-us/";
        String str2 = "https://www.onetwotrip.com/";
        Integer strOffset = str2.length();
        System.out.println(strOffset);
        boolean result = str1.regionMatches(0, str2, 0 , str2.length());
        System.out.println(result);
    }
}
