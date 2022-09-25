package com.ws.masterserver.utils.base;

import com.ws.masterserver.utils.constants.WsCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class WsException extends ResponseStatusException {
    public WsException(WsCode wsCode) {
        super(HttpStatus.valueOf(Integer.parseInt(wsCode.getCode())), wsCode.getMessage());
    }

    public WsException(WsCode wsCode, String message) {
        super(HttpStatus.valueOf(Integer.parseInt(wsCode.getCode())), message);
    }

    public WsException(WsCode wsCode, int code) {
        super(HttpStatus.valueOf(code), wsCode.getMessage());
    }

    public WsException(WsCode wsCode, int code, String message) {
        super(HttpStatus.valueOf(code), message);
    }



}
