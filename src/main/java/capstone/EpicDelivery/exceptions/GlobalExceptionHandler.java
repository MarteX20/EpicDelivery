package capstone.EpicDelivery.exceptions;

import jdk.jshell.spi.ExecutionControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorDetails> userExceptionHandler(UserException ue, WebRequest wr){
        ErrorDetails err = new ErrorDetails();

        err.setLocalDateTime(LocalDateTime.now());
        err.setMessage(ue.getMessage());
        err.setDetails(wr.getDescription(false));

        return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDetails> productExceptionHandler(ProductException pe, WebRequest wr){
        ErrorDetails err = new ErrorDetails();

        err.setLocalDateTime(LocalDateTime.now());
        err.setMessage(pe.getMessage());
        err.setDetails(wr.getDescription(false));

        return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDetails> orderExceptionHandler(ProductException pe, WebRequest wr){
        ErrorDetails err = new ErrorDetails();

        err.setLocalDateTime(LocalDateTime.now());
        err.setMessage(pe.getMessage());
        err.setDetails(wr.getDescription(false));

        return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
    }

}
