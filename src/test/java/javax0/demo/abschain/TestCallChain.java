package javax0.demo.abschain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static javax0.demo.abschain.Log.log;

class TestCallChain {

    @Test
    void test_CextendingA(){
        log.delete(0,log.length());
        new CextendingA().p();
        Assertions.assertEquals("calling ma() in A.p()\n" +
                "calling m() in A.m()\n" +
                "CextendingA.m() is performing\n" +
                "m() returned in A.m()\n" +
                "ma() returned in A.p()\n",log.toString());
    }


    @Test
    void test_CextendingF(){
        log.delete(0,log.length());
        new CextendingF().p();
        Assertions.assertEquals("calling ma() in A.p()\n" +
                "calling mf() in F.ma()\n" +
                "performing extra functionality for F before\n" +
                "calling m() in F.mf()\n" +
                "CextendingF.m() is performing\n" +
                "m() returned in F.mf()\n" +
                "performing extra functionality for F after\n" +
                "mf() returned in F.ma()\n" +
                "ma() returned in A.p()\n",log.toString());
    }

}
