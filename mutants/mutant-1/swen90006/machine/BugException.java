package swen90006.machine;

public class BugException extends Exception {
    public BugException(String msg){
        super("You triggered one of the security bugs!\n" + msg);
    }
}
