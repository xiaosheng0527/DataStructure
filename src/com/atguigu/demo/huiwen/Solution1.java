package com.atguigu.demo.huiwen;

/**
 * @author shengxiao
 * @date 2021/8/1 9:51
 *
 * 字符串 长度为1 的字符串 是最小的回文串
 */
public class Solution1 {
    // Q:返回一个字符串的最长回文子串
    // 注意：牛客17题是求最长回文子串的长度，力扣是返回最长回文子串
    // 暴力破解
    public String longestPalindrome(String s) {
        int len = s.length();
        if (len < 2) {  // 如果字符串长度小于2，则直接返回此字符串作为回文串
            return s;
        }
        int maxLen = 1;     // 回文串的最大长度，其值的重置：是在出现一个子字符串长度> maxLen 且此子字符串是回文串
        int begin = 0;      // 回文串的起始索引位置，其值的重置是：是在出现一个子字符串长度> maxLen 且此子字符串是回文串
        char[] charArray = s.toCharArray();
        // 枚举所有长度严格大于 1 的子串  charArray[i..j]
        for (int i = 0; i < len - 1; i++) {  // 最后一个字符没必要枚举了
            for (int j = i + 1; j < len; j++) {
                // 最长回文串：长度>之前的max，且，是回文串
                // 注意：这里为什么if语句的判断是 j-i+1，因为i和j是子字符串的下标，我们要获取其长度即为j-i+1
                // eg [9,10]中有几个元素， 即为 10 - 9 + 1 【我们要加上本身的那个数】
                if (j - i + 1 > maxLen && isPalindrome(charArray, i, j)) {
                    maxLen = j - i + 1;
                    begin = i;
                }
            }
        }
        return s.substring(begin, begin + maxLen);  // 注意：substring()方法是左闭右开
    }

    private boolean isPalindrome(char[] cs, int i, int j) {
        while (i < j) {     // 如果i >= j ,说明已经比较完成
            if (cs[i] != cs[j]) {   // 如果不相等，就直接退出此方法，不是回文串
                return false;   // 比较的子字符串是不是 回文串 就返回false
            }
            i++;
            j--;
        }
        return true;    // 比较的子字符串是回文串，就返回true
    }

    public static void main(String[] args) {
        Solution1 solution = new Solution1();
        System.out.println(solution.longestPalindrome("babdcbaabcd"));
    }
}
