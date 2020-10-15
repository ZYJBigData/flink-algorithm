package zyj.test.exam.sliding.window;

import java.util.Arrays;

public class FourTest {
    /**
     * 求滑动窗口中的最大值。
     * (1) 使用ordered_map，动态更新，取首元素, NlogK。
     * (2) 维护一个指向最大值的指针，当指针不再在窗口内时，向后移动这一指针到合适的位置。最坏时间复杂度是O（NK），但均摊表现比1还要好。
     * (3）使用单调队列，维护单调递减的队列，队首不再在窗口内时弹出队首，有更大的元素弹出队尾。当前最大元素为队尾元素。接近O（N）。
     * <p>
     * Example 1:
     * <p>
     * Input: nums = [1,3,-1,-3,5,3,6,7], k = 3
     * Output: [3,3,5,5,6,7]
     * Explanation:
     * Window position                Max
     * ---------------               -----
     * [1  3  -1] -3  5  3  6  7       3
     * 1 [3  -1  -3] 5  3  6  7       3
     * 1  3 [-1  -3  5] 3  6  7       5
     * 1  3  -1 [-3  5  3] 6  7       5
     * 1  3  -1  -3 [5  3  6] 7       6
     * 1  3  -1  -3  5 [3  6  7]      7
     * Example 2:
     * <p>
     * Input: nums = [1], k = 1
     * Output: [1]
     * Example 3:
     * <p>
     * Input: nums = [1,-1], k = 1
     * Output: [1,-1]
     * Example 4:
     * <p>
     * Input: nums = [9,11], k = 2
     * Output: [11]
     * Example 5:
     * <p>
     * Input: nums = [4,-2], k = 2
     * Output: [4]
     *
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        int left = 0;
        int[] result = new int[128];
        int max;
        while (left < nums.length - (k - 1)) {
            int[] template = Arrays.copyOfRange(nums, left, left + k);
            Arrays.sort(template);
            max = template[template.length - 1];
            result[left] = max;
            left++;
        }
        return result;
    }

    public static void main(String[] args) {
        FourTest fourTest = new FourTest();
        int[] nums1 = {1, 3, -1, -3, 5, 3, 6, 7};
        int k1 = 3;
        int[] result1 = fourTest.maxSlidingWindow(nums1, k1);
        for (int result : result1) {
            if (result != 0) {
                System.out.print(" result1 value : " + result);
            }
        }

        System.out.println();

        int[] nums2 = {1};
        int k2 = 1;
        int[] result2 = fourTest.maxSlidingWindow(nums2, k2);
        for (int result : result2) {
            if (result != 0) {
                System.out.print(" result2 value : " + result );
            }
        }

        System.out.println();

        int[] nums3 = {1, -1};
        int k3 = 1;
        int[] result3 = fourTest.maxSlidingWindow(nums3, k3);
        for (int result : result3) {
            if (result != 0) {
                System.out.print(" result3 value : " + result);
            }
        }

        System.out.println();

        int[] nums4 = {9,11};
        int k4 = 2;
        int[] result4 = fourTest.maxSlidingWindow(nums4, k4);
        for (int result : result4) {
            if (result != 0) {
                System.out.print(" result4 value : " + result);
            }
        }
        System.out.println();


        int[] nums5 = {4,-2};
        int k5 = 2;
        int[] result5 = fourTest.maxSlidingWindow(nums5, k5);
        for (int result : result5) {
            if (result != 0) {
                System.out.print(" result5 value : " + result);
            }
        }
    }
}
