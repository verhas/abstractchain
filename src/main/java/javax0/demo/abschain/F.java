package javax0.demo.abschain;

import static javax0.demo.abschain.Log.log;

abstract class F extends A{

    protected void ma(){
        log("calling mf() in F.ma()");
        mf();
        log("mf() returned in F.ma()");
    }

    private void mf(){
        log("performing extra functionality for F before");
        log("calling m() in F.mf()");
        m();
        log("m() returned in F.mf()");
        log("performing extra functionality for F after");
    }


}
