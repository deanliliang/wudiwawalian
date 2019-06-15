package com.leyou.bean;

import com.leyou.exception.LyException;
import lombok.Getter;
import org.joda.time.DateTime;

/**
 * @author HuGe
 */
@Getter
public class ExceptionResult {
    private int status;
    private String message;
    private String timestamp;

    public ExceptionResult(LyException e) {
        this.status = e.getStatus();
        this.message = e.getMessage();
        this.timestamp = DateTime.now().toString("yyyy-MM-dd HH:mm:ss");
    }
}