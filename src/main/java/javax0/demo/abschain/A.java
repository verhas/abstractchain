package javax0.demo.abschain;

import static javax0.demo.abschain.Log.log;

abstract class A {

    void p(){
        log("calling ma() in A.p()");
        ma();
        log("ma() returned in A.p()");
    }

    void ma(){
        log("calling m() in A.m()");
        m();
        log("m() returned in A.m()");
    }

    abstract void m();

}
