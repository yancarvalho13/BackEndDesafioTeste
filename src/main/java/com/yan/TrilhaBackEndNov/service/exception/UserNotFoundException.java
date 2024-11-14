package com.yan.TrilhaBackEndNov.service.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super(message);
    }
    public UserNotFoundException(){
        super("Usuário não encontrado");
    }
}
