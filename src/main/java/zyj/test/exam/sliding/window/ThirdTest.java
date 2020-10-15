package zyj.test.exam.sliding.window;

public class ThirdTest {
    /**
     * 给定一个含有 n 个正整数的数组和一个正整数 s ，找出该数组中满足其和 ≥ s 的长度最小的 连续 子数组，并返回其长度。如果不存在符合条件的子数组，返回 0。
     *
     * @param s
     * @param nums
     * @return 示例：
     * <p>
     * 输入：s = 7, nums = [2,3,1,2,4,3]
     * 输出：2
     * 解释：子数组 [4,3] 是该条件下的长度最小的子数组。
     *  
     * <p>
     * 进阶：
     * <p>
     * 如果你已经完成了 O(n) 时间复杂度的解法, 请尝试 O(n log n) 时间复杂度的解法。
     */
    public int minSubArrayLen(int s, int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int sum = 0, min = nums.length + 1;
        int left = 0, right = 0;
        while (right < nums.length) {
            sum = sum + nums[right];
            while (sum >= s) {
                min = Math.min(min, right - left + 1);
                sum = sum - nums[left];
                left++;
            }
            right++;
        }
        return min;
    }

    public static void main(String[] args) {
        int s1 = 7;
        int[] nums1 = {2, 3, 1, 2, 4, 3};

        int s2 = 5;
        int[] nums2 = {2, 1, 4, 3, 1, 5};
        ThirdTest thirdTest = new ThirdTest();
        int result1 = thirdTest.minSubArrayLen(s1, nums1);
        System.out.println("result value :  " + result1);

        int result2 = thirdTest.minSubArrayLen(s2, nums2);
        System.out.println("result value: " + result2);
    }
}

