package com.github.jbharter.delegates;

public class DelegateException extends Exception {
    public enum Type {
        NOT_READY,
        BAD_RETURN,
        UNKNOWN
    }
    public DelegateException() {
        this(null,null,null,null);
    }
    public DelegateException(Type type) {
        this(type,null,null,null);
    }
    public DelegateException(Type type, String message) {
        this(type,null,null,message);
    }
    public DelegateException(Type type, Throwable throwable) {
        this(type,throwable,null,null);
    }
    public DelegateException(Throwable throwable, String message) {
        this(null,throwable,null,message);
    }
    public DelegateException(Exception e) {
        this(null,null,e,null);
    }
    public DelegateException(Type type, Throwable throwable, Exception exception, String message) {
        if (type == null && throwable == null && exception == null && message == null) System.err.println("Encountered no parameter Delegate Exception");
        if (type != null && message != null) System.out.println("Encountered unhandled exception of Type: " + type.name() + " message: " + message);
        if (throwable != null) throwable.printStackTrace();
        if (exception != null) exception.printStackTrace();
    }
}
