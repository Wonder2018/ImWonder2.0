package top.imwonder.myblog.util;
/**
 * StringUtil
 * @description: 字符串工具类
 **/
public class StringUtil {

    /**
     * 判断是否为空字符串最优代码
     * @param str
     * @return 如果为空，则返回true
     */
    public static boolean isEmpty(String str){
        return str == null || str.trim().length() == 0;
    }

    /**
     * 判断字符串是否非空
     * @param str 如果不为空，则返回true
     * @return
     */
    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }

    /**
     * 判断字符串是否全部为空
     * @param str 如果全部为空，则返回true
     * @return
     */
    public static boolean isAllEmpty(String... strs){
        for(String str:strs){
            if(!isEmpty(str)){
                return false;
            }
        }
        return true;
    }
    
    /**
     * 判断字符串是否全部非空
     * @param str 如果全部非空，则返回true
     * @return
     */
    public static boolean isAllNotEmpty(String... strs){
        for(String str:strs){
            if(isEmpty(str)){
                return false;
            }
        }
        return true;
    }
}