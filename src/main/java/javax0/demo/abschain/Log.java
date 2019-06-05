package javax0.demo.abschain;

class Log {
    static final StringBuilder log = new StringBuilder();

    static void log(String s) {
        log.append(s).append("\n");
    }
}
