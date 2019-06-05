package javax0.demo.abschain;

import static javax0.demo.abschain.Log.log;

class CextendingA extends A {

    void m(){
        log(String.format("%s.m() is performing",this.getClass().getSimpleName()));
    }

}
