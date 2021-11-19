package com.atguigu.stack;

/**
 * @author shengxiao
 * @date 2021/7/15 21:34
 *
 * 1. 通过一个 index  值（索引），来遍历我们的表达式
 * 2. 如果我们发现是一个数字, 就直接入数栈
 * 3. 如果发现扫描到是一个符号,  就分如下情况
 *      3.1 如果发现当前的符号栈为 空，就直接入栈
 *      3.2 如果符号栈有操作符，就进行比较,如果当前的操作符的优先级小于或者等于栈中的操作符，
 *          就需要从数栈中pop出两个数,在从符号栈中pop出一个符号，进行运算，将得到结果，入数栈，然后将当前的操作符入符号栈，
 *          如果当前的操作符的优先级大于栈中的操作符， 就直接入符号栈.
 * 4. 当表达式扫描完毕，就顺序的从 数栈和符号栈中pop出相应的数和符号，并运行.
 * 5. 最后在数栈只有一个数字，就是表达式的结果
 */
public class Calculator {

    // 先创建一个栈，直接使用前面创建好的
    // 定义一个ArrayStack 表示栈
    static class ArrayStack2{
        private int maxSize ;   // 栈的大小
        private int[] stack ;   // 数组，数组模拟栈，数据就放在该数组
        private int top = -1 ;

        // 构造器


        public ArrayStack2() {
        }

        public ArrayStack2(int maxSize) {
            this.maxSize = maxSize;
            this.stack = new int[this.maxSize] ;
        }

        // 栈满
        public boolean isFull(){
            return top == maxSize - 1 ;
        }

        // 栈空
        public boolean isEmpty(){
            return top == -1 ;
        }

        // 入栈 - push
        public void push(int value){
            // 先判断栈是否已满
            if(isFull()){
                return ;
            }
            top++ ;
            stack[top] = value ;
        }

        // 出栈
        public int pop(){
            // 先判断是否为空
            if(isEmpty()){
                // 抛出异常
                // 注意：运行时异常【即非受检异常】抛出后，可以不捕获
                throw new RuntimeException("栈空，没有数据") ;
            }
            int value = stack[top];
            top-- ;
            return value ;
        }

        // 显示栈的情况【遍历栈】，遍历时，需要从栈顶开始显示数据
        // 因为栈帧一直指向栈顶，我们只能通过栈帧才能遍历到栈内部的元素
        public void list(){
            if(isEmpty()){
                System.out.println("栈空，没有数据~");
            }
            // 需要从栈顶开始显示数据
            for(int i = top ; i >= 0 ; i--){
                System.out.printf("stack[%d]=%d\n", i,stack[i]);
            }
        }

        // 增加一个方法，可以返回当前栈顶的值，但这不是真正的pop
        // peek：代表读取数据，但不弹栈
        public int peek(){
            return stack[top];
        }

        // 返回运算符的优先级，优先级是程序员来确定，优先级使用数字表示
        // 数字越大，优先级就越高
        // 假定目前的表达式只有 +，-，*，/
        public int priority(int operation){
            if(operation == '*' || operation == '/'){
                return 1 ;
            }else if(operation == '+' || operation == '-'){
                return 0 ;
            }else {
                return -1 ;
            }
        }

        // 判断是不是一个运算符
        public boolean isOperation(char val){
            return val == '+' || val == '-' || val == '*' || val == '/' ;
        }

        // 计算方法
        // 注意：cal()方法是在弹栈的过程中进行计算的，
        //这里定义一个规则，num1先弹栈，num2后弹栈
        // 反过来，即num2先入栈，num1后入栈
        // 注意：表达式默认情况下就是从左向右计算，故num2在前，num1在后
        public int cal(int num1,int num2,int op){
            // result 《-》 简写成：res
            int res = 0 ;   // res  用于存放计算的结果
            // 注意：这里其实在进行四则运算时，都是要将先弹出的节点作为优先级高的，
            // 即res变量 的表达式中，num2写在num1前面
            // 但因为：+和* 运算与num2，num1的顺序无关，故这两个运算过程中，操作数可以调换位置
            switch(op){
                case '+':
                    res = num2 + num1 ;
                    break;
                case '-':
                    res = num2 - num1 ;     // 注意顺序，但如果事先已经确定好了，那就没什么问题了，上面我已经定义了一个规则
                    break;
                case '*':
                    res = num2 * num1 ;
                    break;
                case '/':
                    // 因为先弹出来的数，说明其优先级高，要先进行四则运算，故num2作为被除数
                    res = num2 / num1 ;
                    break;
                default:
                    break ;
            }
            return res ;
        }

    }

    /**
     * 下面这两种解释：异曲同工
     *  1）注意：这里的计算器有问题是因为出栈操作一直从右往左算的，相当于给最后两个数字加上括号，
     *  根据定理，括号前面是'-'或者'/'时，括号里的要变号
     *
     *  2）这里的算法只进行一次比较，例如，7-2*3+1，当+进栈时，会取出3、2 ，然后和 * 计算后将6入栈，
     *  此时+没有再与-进行比较就直接入栈，最后计算的时候先取6、1 ，+ 计算得到7入栈，再取7-7=0 然后入栈，所以结果出错
     *  注意：实际上应该需要通过小括号来确定运算的顺序，这个才不会造成非法的计算
     *  eg：上面的可以修改为  7-（2*3）+1  -》  7-（6-1） -》 （7-5），这才是最后我们所需要的结果
     */
    public static void main(String[] args) {
        // 根据前面老师思路，完成表达式的运算
        //String expression = "3 + 2 * 6 - 2" ;  注意：这里不能添加空格，因为我们会将其当做一个字符读入
        // 而且expression也不能存在一位数以上的数字，如果是70+3-7,则70会被当做两个字符，从而结果错误
        //String expression = "70+2*6-2" ;     // 要如何处理多位数的问题？？？  解决
        String expression = "13-9/3+4" ;    // 但这里处理了多位数，还是出现了问题，等老师的讲解！！！
        // 创建两个栈，数栈，一个符号栈
        ArrayStack2 numStack = new ArrayStack2(10) ;
        ArrayStack2 operationStack = new ArrayStack2(10) ;
        // 定义需要的相关变量
        int index = 0 ;  // 用于扫描
        int num1 = 0 ;
        int num2 = 0 ;
        int op = 0 ;
        int res = 0 ;
        // 注意：char类型的变量空字符，只能用'\0'，
        // 而String类型的引用，空串[""]，其实包括一个'\0'；字符串是由字符连接而成的
        char ch = ' ' ;     // 将每次扫描得到char保存到ch，这里默认值用空格表示
        //System.out.println(ch);   输出即为一个空格
        // 这里考虑到可能会涉及到多个字符的拼接，用StringBuffer可能会好点
        StringBuffer keepNum = new StringBuffer() ;   // 用于拼接多位数

        // 开始while循环的扫描expression
        while(true){
            // 依次得到expression 的每一个字符
            // 注意：substring()方法是非原地操作
            ch = expression.substring(index, index + 1).charAt(0) ; // 一次取出一个字符
            // 判断ch是什么，然后做相应的处理
            if(operationStack.isOperation(ch)){     // 如果是运算符
                // 判断当前的符号栈是否为空
                if(!operationStack.isEmpty()){
                    //如果符号栈有操作符，就进行比较,如果当前的操作符的优先级小于或者等于栈中的操作符, 就需要从数栈中 pop 出两个数,
                    // 在从符号栈中 pop 出一个符号，进行运算，将得到结果，入数栈，然后将当前的操作符入符号栈
                    if(operationStack.priority(ch) <= operationStack.priority(operationStack.peek())){
                        num1 = numStack.pop() ;     // 数栈，栈顶的第一个操作数
                        num2 = numStack.pop() ;     // 数栈，栈顶的下一个操作数
                        op = operationStack.pop() ;     // 符号栈，栈顶的操作符
                        res = numStack.cal(num1, num2, op) ;
                        // 把运算的结果压入数栈
                        numStack.push(res);
                        // 然后将当前的操作符压入符号栈
                        // 为什么？因为之前处于 当前的操作符的优先级小于或者等于栈中的操作符，此时符号还没 入栈
                        // 而上面执行完cal()方法后，结果 压入 数栈，此时就可以把刚开始还未入栈的符号进行压入栈中
                        operationStack.push(ch);
                    } else {
                        //如果当前的操作符的优先级大于栈中的操作符， 就直接入符号栈.
                        operationStack.push(ch);

                    }

                }else{
                    // 如果为空直接压入符号栈
                    operationStack.push(ch);    // 1 + 3
                }
            }else {     // 如果是数，则直接压入数栈【即此时不能考虑操作数】
                /** 注意：但此时不能处理，如果数栈中的数字是大于一位数的情况，故要再进行优化分析
                    // 注意：此时不能直接写成下面的式子，因为字符在内存中是int类型的ASCII码保存，
                    // 字符【char类型】和ascii码【int类型】 是等价 对应替换

                    // (char)数字字符<= 等价转换 =>  对应的ascii码(int)
                    // (一位)数字字符 ！= ascii码 - 48 ，因为ascii码-48 是int型，而数字字符是char型
                    // (故一位)数字 == ascii码(即一位数字字符) - 48
                    // eg '1' 对应的 ascii码为 49，但此时如果我们想要获得int型的1，则需要ascii-48
                    //numStack.push(ch);    // 如果没有减去48，则如果输入的是ch='1',则实际上压入其ascii码49,
                    numStack.push(ch - 48);   // ? "1+3"  '1'=> 1 ，其实这里压入的是int类型的一位数字
                 */

                //分析思路
                //1. 当处理多位数时，不能发现是一个数就立即入栈，因为他可能是多位数
                //2. 在处理数，需要向expression的表达式的index 后再看一位,如果是数就进行扫描，如果是符号才入栈
                //3. 因此我们需要定义一个变量 字符串，用于拼接

                // 处理多位数
                keepNum.append(ch) ;

                // 如果ch已经是expression的最后一位，就直接入栈
                if(index == expression.length() - 1){
                    numStack.push(Integer.parseInt(keepNum.toString())) ;
                }else{
                    // 判断下一个字符是不是数字，如果是数字，就继续扫描，如果是运算符，则入栈
                    if(operationStack.isOperation(expression.substring(index + 1,index + 2).charAt(0))){
                        // 如果后一位是运算符，则入栈  keepNum = "1"  或者 "123"
                        numStack.push(Integer.parseInt(keepNum.toString()));
                        // 重要的！！！！！！，keepNum清空
                        // 此处比较特别，是将StringBuffer中保存的内容清空
                        // delete()方法：删除此序列的子字符串中的字符。 子字符串从指定的start并延伸到索引end - 1处的字符，
                        // 如果不存在这样的字符，则延伸到序列的末尾。 如果start等于end ，则不进行任何更改
                        keepNum.delete(0, keepNum.length()) ;
                        //keepNum.substring(0, 0) ;   // 这样，编译器不会出现异常，因为没有设置异常 ，但此处相当于无效keepNum内容清空，故还是字符串本身,但输出结果感觉还是怪怪的
                    }
                }
            }
            // 让index+1，并判断是否扫描到expression最后，
            index++ ;
            if(index >= expression.length()){
                break ;
            }
        }

        // 当表达式扫描完毕，就顺序的从 数栈 和 符号栈中pop出相应的数和符号，并进行运算
        while(true){
            // 如果符号栈为空，则计算到最后的结果，数栈中只有一个数字【结果】
            if(operationStack.isEmpty()){
                break;
            }
            num1 = numStack.pop() ;
            num2 = numStack.pop() ;
            op = operationStack.pop() ;
            res = numStack.cal(num1, num2, op) ;
            numStack.push(res);     // 入栈

        }
        //将数栈的最后数，pop 出，就是结果
        int res2 = numStack.pop();
        System.out.printf("表达式 %s = %d",expression,res2) ;
    }
}
