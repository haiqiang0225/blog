package blog.seckill.cc.test.service;

import org.springframework.cglib.proxy.Enhancer;

/**
 * description: FiledInjectTest <br>
 * date: 2022/8/28 18:18 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
public class FiledInjectTest {

    public static class Father {
        Integer theInt = 100;

        private Integer myInt = 200;

        public int getMyInt() {
            return myInt;
        }

        public void setMyInt(int myInt) {
            this.myInt = myInt;
        }

    }

    public static class Sub extends Father {
        Integer theInt = 200;

        private Integer myInt = 300;

    }

    public static void main(String[] args) {
        Sub sub = new Sub();
        System.out.println(sub.getMyInt());
    }
}
