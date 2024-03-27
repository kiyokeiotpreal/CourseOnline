package com.example.BaiTest.exceptions;
public class PermissionDenyException extends Exception{
    public PermissionDenyException(String message) {
        super(message);
    }
}