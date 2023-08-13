package com.ssafy.ssap.exception;

public class S3Exception extends RuntimeException{
    public S3Exception(){ super(); }
    public S3Exception(String message, Throwable cause){ super(message,cause); }
    public S3Exception(String message){ super(message); }
    public S3Exception(Throwable cause){ super(cause); }


}
