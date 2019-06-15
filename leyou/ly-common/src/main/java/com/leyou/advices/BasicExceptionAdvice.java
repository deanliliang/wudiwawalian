package com.leyou.advices;

import com.leyou.bean.ExceptionResult;
import com.leyou.exception.LyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.advices
 * @ClassName: BasicExceptionAdvice
 * @Author: Dean
 * @Description: ${description}
 * @Date: 2019/6/13 22:59
 * @Version: 1.0
 */

@ControllerAdvice
@Slf4j
public class BasicExceptionAdvice {

    @ExceptionHandler(LyException.class)
    public ResponseEntity<ExceptionResult> handleLyException(LyException e) {
        return ResponseEntity.status(e.getStatus()).body(new ExceptionResult(e));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(500).body(e.getMessage());
    }
}
