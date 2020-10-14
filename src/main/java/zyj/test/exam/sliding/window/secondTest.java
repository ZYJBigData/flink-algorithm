package zyj.test.exam.sliding.window;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class secondTest {
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
    Map<Character, Integer> needs = new HashMap<>();
    Map<Character, Integer> windows = new HashMap<>();

    public String minWindow(String s, String t) {
        //不符合情况的字符串 直接干掉
        if (s.length() == 0 || t.length() == 0 || " ".equals(s) || " ".equals(t) || t.length() < s.length()) {
            return "";
        }
        //将t放入到needs，（char,字符出现的次数）
        for (int i = 0; i < t.length(); i++) {
            needs.put(t.charAt(i), needs.getOrDefault(t.charAt(i), 0) + 1);
        }
        //窗口左边界，窗口右边界
        int left = 0, right = 0;
        //求最小长度，先置为最大值，最小长度的左边界和右边界
        int len = Integer.MAX_VALUE, ansL = -1, ansR = -1;
        //遍历 窗口往右滑，当窗口右边界的值==字符串的长度时结束
        while (right < s.length()) {
            //每次有边界+1
            right++;
            //如果右边界的值是子串的某一值，将窗口windows的所对应的键值对进行+1操作
            if (right < s.length() && needs.containsKey(s.charAt(right))) {
                windows.put(s.charAt(right), windows.getOrDefault(right, 0) + 1);
            }
            //遍历，右边界右滑保证当前的窗口已经包含子串，这次是左边界左移，求最小窗口值
            while (check() && left <= right) {
                if (right - left + 1 < len) {
                    len = right - left + 1;
                    ansL = left;
                    ansR = right;
                }

                //如果包含左边界的值，当前窗口对应的键值对进行-1操作，然后求下一个满足要求的滑动窗口
                if (needs.containsKey(s.charAt(left))) {
                    windows.put(s.charAt(left), windows.getOrDefault(s.charAt(1), 0) - 1);
                }
                ++left;
            }
        }
        return ansL == -1 ? "" : s.substring(ansL, ansR);
    }

    public Boolean check() {
        Iterator<Map.Entry<Character, Integer>> iterator = needs.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Character, Integer> entry = iterator.next();
            Character character = (Character) entry.getKey();
            Integer integer = (Integer) entry.getValue();
            if (windows.getOrDefault(character, 0) < integer) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {

    }
}
