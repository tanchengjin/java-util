package com.tanchengjin.utils;

/**
 * @author tanchengjin
 * @email 18865477815@163.com
 */
public class IntUtil {
    /**
     * 判断数组是否包含某个键
     *
     * @author TanChengjin
     * @email 18865477815@163.com
     */
    public static boolean arrayContains(int[] arr, int key) {
        for (int ar : arr) {
            if (key == ar) {
                return true;
            }
        }
        return false;
    }


    /**
     * 排除数组中的某个键
     *
     * @Author TanChengjin
     * @Email 18865477815@163.com
     */
    public static int[] excludeByKey(int[] arr, int key) {
        //获取重复次数
        int rpt = countRepeat(arr, key);
        int[] na = new int[arr.length];

        if (rpt >= 1) {
            na = new int[arr.length - rpt];
        }

        int idx = 0;
        for (int i = 0; i < arr.length; i++) {
            if (key != arr[i]) {
                na[idx] = arr[i];
                ++idx;
            }
        }

        return na;
    }

    /**
     * 统计数组某个键重复次数
     *
     * @Author TanChengjin
     * @Email 18865477815@163.com
     */
    public static int countRepeat(int[] array, int key) {
        int r = 0;
        for (int a : array) {
            if (a == key) {
                ++r;
            }
        }
        return r;
    }
}