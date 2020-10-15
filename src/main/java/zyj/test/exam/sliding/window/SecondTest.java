package zyj.test.exam.sliding.window;

public class SecondTest {
    /**
     * 给你一个字符串 S、一个字符串 T 。请你设计一种算法，可以在 O(n) 的时间复杂度内，从字符串 S 里面找出：包含 T 所有字符的最小子串。
     * 示例：
     * <p>
     * 输入：S = "ADOBECODEBANC", T = "ABC"
     * 输出："BANC"
     *  
     * <p>
     * 提示：
     * <p>
     * 如果 S 中不存这样的子串，则返回空字符串 ""。
     * 如果 S 中存在这样的子串，我们保证它是唯一的答案。
     */
    public String minWindow(String s, String t) {
        if (s == null || s == "" || t == null || t == "" || s.length() < t.length()) {
            return "";
        }
        //用来统计t中每个字符出现次数
        int[] needs = new int[128];
        //用来统计滑动窗口中每个字符出现次数
        int[] window = new int[128];

        for (int i = 0; i < t.length(); i++) {
            needs[t.charAt(i)]++;
        }

        int left = 0;
        int right = 0;

        String res = "";

        //目前有多少个字符
        int count = 0;

        //用来记录最短需要多少个字符。
        int minLength = s.length() + 1;

        while (right < s.length()) {
            char ch = s.charAt(right);
            window[ch]++;
            if (needs[ch] > 0 && needs[ch] >= window[ch]) {
                count++;
            }

            //移动到不满足条件为止
            while (count == t.length()) {
                ch = s.charAt(left);
                if (needs[ch] > 0 && needs[ch] >= window[ch]) {
                    count--;
                }
                if (right - left + 1 < minLength) {
                    minLength = right - left + 1;
                    res = s.substring(left, right + 1);

                }
                window[ch]--;
                left++;

            }
            right++;

        }
        return res;
    }


    public static void main(String[] args) {
        String str1 = "ASDFGHJKQWG", tis1 = "GQW";
        String str2 = "ADOBECODEBANC", tis2 = "ABC";
        String str3 = "DBADBEECCODEBANCC", tis3 = "ABCC";

        SecondTest secondTest = new SecondTest();
        String result1 = secondTest.minWindow(str1, tis1);
        System.out.println("str1 result value: " + result1);

        String result2 = secondTest.minWindow(str2, tis2);
        System.out.println("str2 result value: " + result2);

        String result3 = secondTest.minWindow(str3, tis3);
        System.out.println("str3 result value: " + result3);
    }
}
