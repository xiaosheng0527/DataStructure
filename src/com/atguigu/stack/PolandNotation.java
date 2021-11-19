package com.atguigu.stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * @author shengxiao
 * @date 2021/7/16 15:59
 *
 * 小技巧：输出List<String>中元素的时候不会将双引号一起输出，因为这个要给用户看的，就像我们在搜索框中输出的信息就是String类型的，
 *       而服务器返回的数据也是String类型的，但其没有将双引号一并返回
 *
 * 前缀：prefix
 * 中缀：infix
 * 后缀  suffix
 *
 * 重点：对于栈这个数据结构，压栈会添加数据，弹栈理论上会弹出数据；
 * 但是切记：压栈和弹栈本质上都是栈顶指针的移动，就像弹栈过程中，其实元素还是在那里，只是栈顶指针没有指向它，故无法访问
 *          如果此时又进行压栈，那么刚才被弹栈的元素相当于被覆盖。
 *
 * 字符\的功能：将下一字符标记为特殊字符、文本、反向引用或八进制转义符。
 * 例如，"n"匹配字符"n"。"\n"匹配换行符。序列"\\"匹配"\"，"\("匹配"("。
 * 注意：字符：'\d'    功能：数字字符匹配。等效于 [0-9]。
 *      如果写正则表达式时候，直接写\d,则会直接显示语法错误【因为如果想直接匹配单个字符d，就直接写d即可；而\会将下一字符标记为特殊字符，但d不是特殊字符】，
 *      而如果写为\\d，则可以匹配出 '\d' 即数字字符
 * 后缀表达式实现一个运算器【从左向右遍历】
 *
 * // 这里有个规定：因为逆波兰【后缀】表达式是从左向右遍历的，故假设左边的数(num1)会被先压栈，右边的数(num2)会后被压栈
 * // 故出栈时候，num2先弹栈，num1后弹栈，即最后进行四则运算时，都是num1在前，num2在后
 * // 综上：怎么看弹栈后两个数的四则运算过程中，谁在前面，主要是看刚开始表达式的遍历顺序
 * // 如果是前缀表达式，则是从右向左遍历，故假设右边的数(num1)会被先压栈，左边的数(num2)会后被压栈
 * // 故出栈时候，num2先弹栈，num1后弹栈，即最后进行四则运算时，就是num1在前，num2在后
 * // 而如果是中缀表达式，则是从左向右遍历，故假设左边的数(num1)会被先压栈，右边的数(num2)会后被压栈
 * // 故出栈时候，num2先弹栈，num1后弹栈，即最后进行四则运算时，就是num1在前，num2在后
 */
public class PolandNotation {


    // 将一个逆波兰表达式，依次将数据和运算符 放入到 ArrayList中
    public static List<String> getListString(String suffixExpression){
        // 将suffixExpression 分割
        String[] split = suffixExpression.split(" ") ;
        List<String> list = new ArrayList<>() ;
        for(String element: split){
            list.add(element) ;
        }
        return list ;
    }

    // 完成对逆波兰表达式的运算

    /**例如计算如下表达式：(3+4)*5-6  => 3 4 + 5 * 6 -
     1) 从左至右扫描，将3和4压入堆栈；
     2) 遇到+运算符，因此弹出4和3（4为栈顶元素，3为次顶元素），计算出3+4的值，得7，再将7入栈；
     3) 将5入栈；
     4) 接下来是×运算符，因此弹出5和7，计算出7×5=35，将35入栈；
     5) 将6入栈；
     6) 最后是-运算符，计算出35-6的值，即29，由此得出最终结果
     *
     */

    public static int calculate(List<String> suffixList){
        // 创建个栈，只需要一个栈即可
        Stack<String> stack = new Stack<>() ;
        // 遍历 suffixList
        for(String item : suffixList){
            // 这里使用正则表达式，匹配所有的数字
            // \d+：代表匹配至少一个数字字符
            if(item.matches("\\d+")){   // 注意：这里多写一个\是为了匹配特殊字符，这里的特殊字符为'\d'
                // 入栈
                stack.push(item) ;
            }else{
                // pop出两个数，并运算，再入栈
                // 这里有个规定：因为逆波兰【后缀】表达式是从左向右遍历的，故假设左边的数(num1)会被先压栈，右边的数(num2)会后被压栈
                // 故出栈时候，num2先弹栈，num1后弹栈，即最后进行四则运算时，都是num1在前，num2在后
                // 综上：怎么看弹栈后两个数的四则运算过程中，谁在前面，主要是看刚开始表达式的遍历顺序
                // 如果是前缀表达式，则是从右向左遍历，故假设右边的数(num1)会被先压栈，左边的数(num2)会后被压栈
                // 故出栈时候，num2先弹栈，num1后弹栈，即最后进行四则运算时，就是num1在前，num2在后
                // 而如果是中缀表达式，则是从左向右遍历，故假设左边的数(num1)会被先压栈，右边的数(num2)会后被压栈
                // 故出栈时候，num2先弹栈，num1后弹栈，即最后进行四则运算时，就是num1在前，num2在后
                int num2 = Integer.parseInt(stack.pop());
                int num1 = Integer.parseInt(stack.pop());
                int res = 0 ;
                if("+".equals(item)){
                    res = num1 + num2 ;
                }else if("-".equals(item)){
                    res = num1 - num2 ;
                }else if("*".equals(item)){
                    res = num1 * num2 ;
                }else if("/".equals(item)){
                    res = num1 / num2 ;
                }else{
                    throw new RuntimeException("运算符有误") ;
                }
                // 把res 入栈
                //stack.push(String.valueOf(res)) ;   // 将整数转换成字符串
                stack.push("" + res) ;  // 同样也行
            }
        }
        // 最后留在stack中的数据是运算结果【注意：最后留在stack的栈顶数据就是最后的运算结果，只有一个数据】
        return Integer.parseInt(stack.pop());
    }

    // 方法： 将中缀表达式转换成对应的List
    // s = "1+((2+3)×4)-5"
    // infix：中缀
    public static List<String> toInfixExpressionList(String s){
        // 定义一个List,存放中缀表达式 对应的内容
        List<String> list = new ArrayList<>() ;
        int i = 0 ;     // 这相当于一个指针，用于遍历 中缀表达式 字符串
        String str ;    // 对多位数的拼接
        char c ;    // 每遍历到一个字符，就放入到c
        do{
            // 如果c是一个非数字,我需要加入到list中
            // 注意：数字字符'0'~'9' ascii码 为 48~57
            if((c = s.charAt(i)) < 48 || (c = s.charAt(i)) > 48 + 10 - 1){
                list.add("" + c) ;
                i++ ;   // i需要后移
            } else{     // 如果是一个数，需要考虑可能出现多位数的情况
                str = "" ;  // 先将str 置成""   '0'[48]->'9'[57]
                // 通过while循环，判断是否存在多位数
                while(i < s.length() && (c = s.charAt(i)) >= 48 && (c = s.charAt(i)) <= 57){
                    str += c ;  // 拼接
                    i++ ;   // i也要后移
                }
                list.add(str) ;
            }
        }while (i < s.length()) ;
        return list ;
/*        // 检查是否是带符号的数字
        // 1. 带正负号且前一个字符为运算符（i=0时直接带正负号的也是数字）
        // 2. 当前字符为数字
        List<String> list = new ArrayList<>() ;
        int i = 0 ;     // 这相当于一个指针，用于遍历 中缀表达式 字符串
        StringBuffer keepNum = new StringBuffer() ;    // 对多位数的拼接
        char c ;    // 每遍历到一个字符，就放入到c
        boolean flag = false ;  // 默认情况下不是正负号【为运算符】，反之即为正负号
        while(true){     // s：代表字符串
            // 检查是否是带符号的数字
            // 1. 带正负号且前一个字符为运算符（i=0时直接带正负号的也是数字）
            // 2. 当前字符为数字
            c = s.substring(i, i + 1).charAt(0) ;
            switch(c){
                case '(':
                    list.add("" + c) ;
                    i++ ;
                case ')':
                    list.add("" + c) ;
                    i++ ;
                case '+':
                case '-':



                    if(i == 0){
                        String rearStr = s.substring(i+1, i+2) ;
                        if(isNumeric(rearStr)){
                            flag = true ;
                            i++ ;
                            break ;
                        }
                    }
                    if(i >= s.length()){
                        return list ;
                    }
                    String preStr = s.substring(i-1, i) ;

                    if(i <= s.length() - 1){
                        if(i != s.length() -1){
                            String rearStr = s.substring(i+1, i+2) ;
                            if((!isNumeric(preStr) || ")".equals(preStr)) && isNumeric(rearStr) ){
                                flag = true ;
                                i++ ;
                                break ;
                            }else{

                            }
                        }
                    }
                    //char tempChar = s.substring(i-1, i).charAt(0) ;

            }

        }*/
    }

    public   static   boolean  isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    public static List<String> parseSuffixExpressionList(List<String> list){
        // 定义两个栈
        Stack<String> s1 = new Stack<>() ;
        // 说明：因为s2这个栈，在整个转换过程中，没有pop操作，而且后面我们还需要逆序输出
        // 因为比较麻烦，这里我们就不用Stack<String> s2 = new Stack<>() 直接使用List<String> s2
        //Stack<String> s2 = new Stack<>() ;
        List<String> s2 = new ArrayList<>() ;   // 存储中间结果的s2

        // 遍历list
        for (String item:list) {
            // 如果是一个数，就入栈s2
            if(item.matches("\\d+")){   // 遇到操作数
                s2.add(item) ;
                // 下面两个else if()语句是进行编写 遇到括号时候的代码
            }  else if("(".equals(item)){    // '(' == (item.charAt(0)) 也行，但太麻烦
                s1.push(item);  // 压栈
            } else if(")".equals(item)){
                // peek() :查看此堆栈顶部的对象而不将其从堆栈中移除。
                //  如果是右括号“)”，则依次弹出s1栈顶的运算符，并压入s2，
                //  直到遇到左括号为止，此时将这一对括号丢弃
                while(!"(".equals(s1.peek())){

                    s2.add(s1.pop()) ;
                }
                s1.pop() ;  // 将"("从s1栈弹出，消除小括号
            } else{     // 遇到操作符：这里是执行四则运算的操作符
                /*  切记：不能再这里判断是否为空，因为如果这样写刚开始s1为空，然后进行压栈，而下面还有一个s1.push(item);造成重复压栈，故可以稍作修改
                         最好还是按照老师的写法，使得代码不会冗余。
                if(s1.empty()){     // 当操作符栈为空时，直接压入
                    s1.push(item) ;
                }*/
                // 当item的优先级小于等于s1栈顶运算符，
                // 将s1栈顶的运算符弹出并压入到s2中，再次转到(4.1)与s1中新的栈顶运算符相比较
                // 问题：我们缺少一个比较优先级高低的方法
                while(s1.size() != 0 && Operation.getValue(s1.peek()) >= Operation.getValue(item)){
                    s2.add(s1.pop()) ;
                }
                // 执行下面的代码是因为：s1为空的情况，或者item的优先级大于s1栈顶的运算符，
                // 还需要将item压入栈中
                // 切记：此代码适用于如果s1为空，s1不为空都必须执行的代码
                s1.push(item) ;
            }
        }

        //将s1中剩余的运算符依次弹出并压入s2
        while(s1.size() != 0){
            s2.add(s1.pop()) ;
        }

        return s2 ; // 因为是存放到List，因此按顺序输出就是对应的后缀表达式对应的List
    }

    // 编写一个类    Operation  可以返回一个运算符  对应的优先级
    static class Operation{
        private static int ADD = 1 ;
        private static int SUB = 1 ;
        private static int MUL = 2 ;
        private static int DIV = 2 ;

        // 写一个方法，返回对应的优先级数字
        public static int getValue(String operation){
            int result = 0 ;
            switch (operation){
                case "+" :
                    result = ADD ;
                    break ;
                case "-" :
                    result = SUB ;
                    break ;
                case "*" :
                    result = MUL ;
                    break ;
                case "/" :
                    result = DIV ;
                    break ;
                default:
                    System.out.println("不存在该运算符");
                    break ;
            }
            return result ;
        }
    }

    /**
     *
     * 注意：这里没有实现负数和小数的运算，如果是负数，代码会直接报错；
     * 这里的负数不是算出结果的值，而是在输入的时候如果输入负数；eg（-2+3），那么-2就是负数，
     * 因为前面中缀表达式转换成列表的时候没有考虑到-2，因为只要看到操作符，就直接作为一个字符串，故list就会将-2拆开，生成[-,2,3]
     * 所以，只要考虑了这个因素，应该就可以保证可以实现负数的运算
     */
    public static void main(String[] args) {

        // 完成将一个中缀表达式转成后缀表达式的功能
        // 说明
        //1. 1+((2+3)×4)-5 => 转成  1 2 3 + 4 × + 5 –  => 16
        //2. 因为直接对str 进行操作，不方便，因此 先将  "1+((2+3)×4)-5" =》 中缀的表达式对应的List
        //   即 "1+((2+3)×4)-5" => ArrayList [1,+,(,(,2,+,3,),*,4,),-,5]
        //3. 将得到的中缀表达式对应的List => 后缀表达式对应的List
        //   即 ArrayList [1,+,(,(,2,+,3,),*,4,),-,5]  =》 ArrayList [1,2,3,+,4,*,+,5,–]
        String expression = "1-((2+3)*4)-5" ;
        //String expression = "-1+((-2-3)*4)-5" ;

        List<String> infixExpressionList = toInfixExpressionList(expression) ;
        System.out.println("中缀表达式对应的List" + infixExpressionList);    // ArrayList [1,+,(,(,2,+,3,),*,4,),-,5]

        List<String> suffixExpressionList = parseSuffixExpressionList(infixExpressionList) ;
        System.out.println("后缀表达式对应的List" + suffixExpressionList);

        System.out.printf("expression=%d",calculate(suffixExpressionList));

        /*// 先定义一个逆波兰表达式
        // (3+4)*5-6    ==> 3 4 + 5 * 6 -   => 164
        // 4 * 5 - 8 + 60 + 8 / 2   => 4 5 * 8 - 60 + 8 2 / +
        // 上面数字和符号用空格隔开，故下面我们要进行判断，除去空白字符
        // suffix：后缀
        // 说明为了方便，逆波兰表达式 的数字和符号使用空格隔开
        String suffixExpression = "4 5 * 8 - 60 + 8 2 / +" ;
        // 思路
        // 1. 先将 "3 4 + 5 * 6 - " => 放到ArrayList中
        // 2. 将ArrayList 传递给一个方法，遍历ArrayList 配合栈 完成计算

        List<String> suffixList = getListString(suffixExpression) ;
        //System.out.println(suffixList.size());
        System.out.println("suffixList=" + suffixList);

        int res = calculate(suffixList) ;
        System.out.println("计算的结果是=" + res);*/

        //String[] a = new String["fd","eet","fdg"] ;
    }
}
