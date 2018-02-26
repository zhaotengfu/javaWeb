package com.ztf;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ztf
 * @version 1.0
 *          <p>
 *          2018-2-1
 *          </P>
 */
public class TestJavaRule {
    public static void main(String[] args) {
       TestJavaRule testRule = new TestJavaRule();
//        //正则表达式一个helloWorld
//        testRule.test1();
//        // 项目中的一个正则表达式
//        testRule.test2();
//        //java中一些正则表达式
//        testRule.test3();
//        //测试元字符\,^,$,*,+,？
//        testRule.test4();
//        //测试元字符{n}，{n,}，{n,m}，.点
//        testRule.test5();
        //测试元字符(pattern)，(?:pattern)，(?=pattern)，(?!pattern)，(?<=pattern)，(?<!pattern)
 //       testRule.test6();
        //x|y,[xyz],[^xyz],[a-z],[^a-z],
        //testRule.test7();
        // \b,\B,\cx
        //testRule.test8();
        /**
         * \d,\D,\f，\n，\r，\s，\S，\t，\v，\w，\W
         *\xn，\num，\n，\nm，\nml ，
         * **/
        testRule.test9();
    }

    //正则表达式一个helloWorld
    public void test1(){
        // 按指定模式在字符串查找
        String line = "This order was placed for QT3000! OK?";
        String pattern = "(\\D*)(\\d+)(.*)";
        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);

        // 现在创建 matcher 对象
        Matcher m = r.matcher(line);
        if (m.find()) {
            System.out.println("Found value: " + m.group(0) );
            System.out.println("Found value: " + m.group(1) );
            System.out.println("Found value: " + m.group(2) );
            System.out.println("Found value: " + m.group(3) );
        } else {
            System.out.println("NO MATCH");
        }
    }

    //项目中的一个正则表达式
    public void test2(){
        Pattern pattern = Pattern.compile("\\{([^{}]+)\\}");
        Matcher matcher = pattern.matcher("{dafl}{nd}{%d}");
        while (matcher.find()){
            System.out.println("bbbbb="+matcher.group(1));
        }
    }

    /**
     * matches:整个匹配，只有整个字符序列完全匹配成功，才返回True，否则返回False。
     *          但如果前部分匹配成功，将移动下次匹配的位置。
     *lookingAt:部分匹配，总是从第一个字符进行匹配,匹配成功了不再继续匹配，匹配失败了,也不继续匹配。
     *find: 部分匹配，从当前位置开始匹配，找到一个匹配的子串，将移动下次匹配的位置。
     *reset: 给当前的Matcher对象配上个新的目标，目标是就该方法的参数；如果不给参数，reset会把Matcher设到当前字符串的开始处。
     * **/
    public void test3(){
        //数字大于等于三小于5
        Pattern pattern = Pattern.compile("\\d{3,5}");
        String charSequence = "123-34345-234-00";
        Matcher matcher = pattern.matcher(charSequence);

        //虽然匹配失败，但由于charSequence里面的"123"和pattern是匹配的,所以下次的匹配从位置4开始
        print(matcher.matches());
        //测试匹配位置
        matcher.find();
        print(matcher.start());
        //使用reset方法重置匹配位置
        matcher.reset();

        //第一次find匹配以及匹配的目标和匹配的起始位置
        print(matcher.find());
        print(matcher.group()+" - "+matcher.start());
        //第二次find匹配以及匹配的目标和匹配的起始位置
        print(matcher.find());
        print(matcher.group()+" - "+matcher.start());

        //第一次lookingAt匹配以及匹配的目标和匹配的起始位置
        print(matcher.lookingAt());
        print(matcher.group()+" - "+matcher.start());

        //第二次lookingAt匹配以及匹配的目标和匹配的起始位置
        print(matcher.lookingAt());
        print(matcher.group()+" - "+matcher.start());
    }

    /***
     * 测试元字符\,^,$,*,+,？
     * +？非贪婪模式与贪婪模式
     */
    public void test4(){
        String rule = "^\\{\\d*?\\D+?\\}";
        String ss1 = "{12SDFFF}";
        Pattern pattern1 = Pattern.compile(rule);
        Matcher matcher1 = pattern1.matcher(ss1);
        Boolean booll = matcher1.matches();
        //使用reset方法重置匹配位置
        matcher1.reset();
        Boolean bool = matcher1.find();
        System.out.println("bool="+bool+"blool1="+booll);
        String str = matcher1.replaceAll("AAAAA");
        System.out.println("str = "+str);

    }

    /***
     * 测试元字符{n}，{n,}，{n,m}，.点
     * **/
    public void test5(){
        String regex = "\\{{2}o{4,}\\d{3,5}";
        String ss = "{{oooo111";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher1 = pattern.matcher(ss);
        print("matches验证："+matcher1.matches());
        matcher1.reset();
        print("find验证："+matcher1.find());
        print("lookingAt验证："+matcher1.lookingAt());
    }

    /**
     * 组
     * 测试元字符(pattern)，(?:pattern)，(?=pattern)，(?!pattern)，(?<=pattern)，(?<!pattern)
     * ***/
    public void test6(){
        String regex = "(\\d+)(\\D*)";
        String ss = "1234adfdf";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ss);
        while (matcher.find()){
            print("matcher1.group(0)="+matcher.group(0));
            print("matcher1.group(1)="+matcher.group(1));
            print("matcher1.group(2)="+matcher.group(2));
        }
        String regex1 = "industr(?:y|ies)";
        String ss1 = "industry";
        Pattern pattern1 = Pattern.compile(regex1);
        Matcher matcher1 = pattern1.matcher(ss1);
        print("(?:pattern)验证="+matcher1.matches());

        String regex2 = "Windows(?=95|98|NT|2000)";
        String ss2 = "Windows2000";
        Pattern pattern2 = Pattern.compile(regex2);
        Matcher matcher2 = pattern2.matcher(ss2);
        print("(?=pattern)验证="+matcher2.find()+" matcher2.group="+matcher2.group());

        String regex3 = "Windows(?!95|98|NT|2000)";
        String ss3 = "Windows3.1";
        Pattern pattern3 = Pattern.compile(regex3);
        Matcher matcher3 = pattern3.matcher(ss3);
        print("(?!pattern)验证="+matcher3.find()+" matcher3.group="+matcher3.group());

        //反向肯定预查
        String regex4 = "(?<=95|98|NT|2000)Windows";
        String ss4 = "2000Windows";
        Pattern pattern4 = Pattern.compile(regex4);
        Matcher matcher4 = pattern4.matcher(ss4);
        print("(?<=pattern)验证="+matcher4.find()+" matcher4.group="+matcher4.group());

        //正向否定预查
        String regex5 = "(?<!95|98|NT|2000)Windows";
        String ss5 = "3.1Windows";
        Pattern pattern5 = Pattern.compile(regex5);
        Matcher matcher5 = pattern5.matcher(ss5);
        print("(?<!pattern)验证="+matcher5.find()+" matcher5.group="+matcher5.group());

    }


    /**
     * x|y,[xyz],[^xyz],[a-z],[^a-z],
     * **/
    public void test7(){
        //z|food”能匹配“z”或“food”或"zood"(此处请谨慎)。“(z|f)ood”则匹配“zood”或“food”
        String regex1 = "z|food";
        String ss1 = "food";
        Pattern pattern1 = Pattern.compile(regex1);
        Matcher matcher1 = pattern1.matcher(ss1);
        print("x|y测试1:="+matcher1.matches());

        String regex2 = "(z|f)ood";
        String ss2 = "food";
        Pattern pattern2 = Pattern.compile(regex2);
        Matcher matcher2 = pattern2.matcher(ss2);
        print("x|y测试2:="+matcher2.matches()+"matcher2.group()="+matcher2.group());

        String regex3 = "[abc]";
        String ss3 = "plain";
        Pattern pattern3 = Pattern.compile(regex3);
        Matcher matcher3 = pattern3.matcher(ss3);
        print("[abc]测试:="+matcher3.find()+"matcher3.group()="+matcher3.group());

        String regex4 = "[^abc]";
        String ss4 = "plain";
        Pattern pattern4 = Pattern.compile(regex4);
        Matcher matcher4 = pattern4.matcher(ss4);
        while(matcher4.find()){
            print("start="+matcher4.start()+" [^abc]测试:="+matcher4.find()+"matcher4.group()="+matcher4.group()+" end="+matcher4.end());
        }

        String regex5 = "[0-9]";
        String ss5 = "pyad2354xa";
        Pattern pattern5 = Pattern.compile(regex5);
        Matcher matcher5 = pattern5.matcher(ss5);
        while (matcher5.find()){
            print("start="+matcher5.start()+" [a-x]测试:="+matcher5.find()+"matcher5.group()="+matcher5.group()+" end="+matcher5.end());
        }

        String regex6 = "[^a-x]";
        String ss6 = "pyadxa";
        Pattern pattern6 = Pattern.compile(regex6);
        Matcher matcher6 = pattern6.matcher(ss6);
        while (matcher6.find()){
            print("start="+matcher6.start()+" [^a-x]测试:="+matcher5.find()+"matcher6.group()="+matcher6.group()+" end="+matcher6.end());
        }
    }

    /**
     * \b,\B,\cx
     * **/
    public void test8(){
        String regex1 = "er\\b";
        String ss1 = "er ewwewer";
        Pattern pattern1 = Pattern.compile(regex1);
        Matcher matcher1 = pattern1.matcher(ss1);
        System.out.println("\\b="+matcher1.matches());
        matcher1.reset();
        while (matcher1.find()){
            print("\\bstart="+matcher1.start()+" \\b:="+matcher1.find()+" \\bmatcher1.group()="+matcher1.group(0)+" end="+matcher1.end());
        }

        String regex2 = "er\\B";
        String ss2 = "erewwewer1231";
        Pattern pattern2 = Pattern.compile(regex2);
        Matcher matcher2 = pattern2.matcher(ss2);
        System.out.println("\\B="+matcher2.matches());
        matcher2.reset();
        while (matcher2.find()){
            print("test\\B="+matcher2.group());
            print("start="+matcher2.start()+" \\b:="+matcher2.find()+"matcher2.group()="+matcher2.group()+" end="+matcher2.end());
        }

        //不懂啊
        String regex3 = "\\cM";
        String ss3 = "Control-Maaaa-M";
        Pattern pattern3 = Pattern.compile(regex3);
        Matcher matcher3 = pattern3.matcher(ss3);
        System.out.println("\\cx="+matcher3.matches());
        matcher3.reset();
        while (matcher3.find()){
            print("test\\cx="+matcher3.group());
            print("\\cx:start="+matcher3.start()+" \\cx:="+matcher3.find()+"matcher2.group()="+matcher3.group()+" end="+matcher3.end());
        }
    }

    /**
     * \d,\D,\f，\n，\r，\s，\S，\t，\v，\w，\W
     *\xn，\num，\n，\nm，\nml
     * **/
    public void test9() {
        //数字字符匹配。等效于 [0-9]。
        String regex1 = "\\d";
        String ss1 = "1";
        Pattern pattern1 = Pattern.compile(regex1);
        Matcher matcher1 = pattern1.matcher(ss1);
        System.out.println("\\d="+matcher1.matches());
        //非数字字符匹配。等效于 [^0-9]。。
        String regex2 = "\\D";
        String ss2 = "a";
        Pattern pattern2 = Pattern.compile(regex2);
        Matcher matcher2 = pattern2.matcher(ss2);
        System.out.println("\\D="+matcher2.matches());
        print("\\f换页符匹配。等效于 \\x0c 和 \\cL。");
        print("\\n换行符匹配。等效于 \\x0a 和 \\cJ。");
        print("\\r匹配一个回车符。等效于 \\x0d 和 \\cM。");
        print("\\s匹配任何空白字符，包括空格、制表符、换页符等。与 [ \\f\\n\\r\\t\\v] 等效。");
        print("\\S匹配任何非空白字符。与 [^ \\f\\n\\r\\t\\v] 等效。");
        print("\\t制表符匹配。与 \\x09 和 \\cI 等效。");
        print("\\v垂直制表符匹配。与 \\x0b 和 \\cK 等效。");
        print("\\w匹配任何字类字符，包括下划线。与\"[A-Za-z0-9_]\"等效。");
        print("\\W与任何非单词字符匹配。与\"[^A-Za-z0-9_]\"等效。");
        print("\\xn匹配 n，此处的 n 是一个十六进制转义码。十六进制转义码必须正好是两位数长。例如，\"\\x41\"匹配\"A\"。\"\\x041\"与\"\\x04\"&\\1等效。允许在正则表达式中使用 ASCII 代码。");
        print("\\num匹配 num，此处的 num 是一个正整数。到捕获匹配的反向引用。例如，\"(.)\\1\"匹配两个连续的相同字符。");
        print("\\n标识一个八进制转义码或反向引用。如果 \\n 前面至少有 n 个捕获子表达式，那么 n 是反向引用。否则，如果 n 是八进制数 (0-7)，那么 n 是八进制转义码。");
        print("\\nm标识一个八进制转义码或反向引用。如果 \\nm 前面至少有 nm 个捕获子表达式，那么 nm 是反向引用。如果 \\nm 前面至少有 n 个捕获，则 n 是反向引用，后面跟有字符 m。如果两种前面的情况都不存在，则 \\nm 匹配八进制值 nm，其中 n 和 m 是八进制数字 (0-7)。");
        print("\\nml当 n 是八进制数 (0-3)，m 和 l 是八进制数 (0-7) 时，匹配八进制转义码 nml。");
        print("\\un匹配 n，其中 n 是以四位十六进制数表示的 Unicode 字符。例如，\\u00A9 匹配版权符号 (?)。");
    }

    public static void print(Object o){
        System.out.println(o);
    }
}
