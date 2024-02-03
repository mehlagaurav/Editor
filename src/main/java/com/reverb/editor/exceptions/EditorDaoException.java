package com.reverb.editor.exceptions;

public class EditorDaoException extends RuntimeException{
    private int errorCode;

    public EditorDaoException(int errorCode,String message){
        super(message);
        this.errorCode=errorCode;
    }

    public EditorDaoException(String message){
        super(message);
    }
    public EditorDaoException(String message, Exception exception) {
        super(message, exception);
    }
    public EditorDaoException() {
        super();
    }
}
