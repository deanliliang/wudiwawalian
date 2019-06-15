package com.leyou.exception;

import com.leyou.enums.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @author
 */

@Getter
public class LyException extends RuntimeException {
    /**
     * 异常状态码
     */
    private int status;

    public LyException(ExceptionEnum em) {
        super(em.getMessage());
        this.status = em.getStatus();
    }

    public LyException(ExceptionEnum em, Throwable cause) {
        super(em.getMessage(), cause);
        this.status = em.getStatus();
    }

}
