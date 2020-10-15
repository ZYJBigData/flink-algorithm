package zyj.test.exam.sliding.window;

import java.util.HashMap;
import java.util.Map;

/**
 * 维护一个不含重复字符的滑动窗口。需要记录每个字符最后出现的位置，当遇到重复字符的时候，就把窗口首部调到上一次出现这个字符的下一个位置..
 * Given a string s, find the length of the longest substring without repeating characters.
 * Example 1:
 * <p>
 * Input: s = "abcabcbb"
 * Output: 3
 * Explanation: The answer is "abc", with the length of 3.
 * Example 2:
 * <p>
 * Input: s = "bbbbb"
 * Output: 1
 * Explanation: The answer is "b", with the length of 1.
 * Example 3:
 * <p>
 * Input: s = "pwwkew"
 * Output: 3
 * Explanation: The answer is "wke", with the length of 3.
 * Notice that the answer must be a substring, "pwke" is a subsequence and not a substring.
 * Example 4:
 * <p>
 * Input: s = ""
 * Output: 0
 */
public class FirstTest {
    public int lengthOfLongestSubString(String s) {
        if (s.length() == 0) {
            return 0;
        }
        int max = 0;
        int start = 0;
        Map<Character, Integer> map = new HashMap();
        for (int end = 0; end < s.length(); end++) {
            Character character = s.charAt(end);
            if (map.containsKey(character)) {
                start = Math.max(start, map.get(character) + 1);
            }
            map.put(character, end);
            max = Math.max(max, end - start + 1);
        }
        return max;
    }

    public static void main(String[] args) {
        String str1 = "abcabcbb";
        String str2 = "bbbbb";
        String str3 = "pwwkew";
        String str4 = "";
        FirstTest firstTest = new FirstTest();
        int max1 = firstTest.lengthOfLongestSubString(str1);
        System.out.println(max1);
        int max2 = firstTest.lengthOfLongestSubString(str2);
        System.out.println(max2);
        int max3 = firstTest.lengthOfLongestSubString(str3);
        System.out.println(max3);
        int max4 = firstTest.lengthOfLongestSubString(str4);
        System.out.println(max4);
    }
}
