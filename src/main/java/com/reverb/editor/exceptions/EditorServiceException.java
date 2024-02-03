package com.reverb.editor.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditorServiceException extends RuntimeException{
    private int errorCode;

    public EditorServiceException(int errorCode,String message){
        super(message);
        this.errorCode=errorCode;
    }

    public EditorServiceException(String message){
        super(message);
    }
}
