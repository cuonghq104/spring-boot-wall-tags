package ptit.cuonghq.walltag.models.commons;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseFactory {

    public static ResponseEntity<ResponseObjectResult> created(String message, Object data) {
        ResponseObjectResult created = new ResponseObjectResult(true, 201, message, data);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    public static ResponseEntity<ResponseObjectResult> authorizationError() {
        ResponseObjectResult authorization = new ResponseObjectResult(false, 401, "Authorization error", null);
        return new ResponseEntity<>(authorization, HttpStatus.UNAUTHORIZED);
    }

    public static ResponseEntity<ResponseObjectResult> ok(String message, Object data) {
        ResponseObjectResult ok = new ResponseObjectResult(true, 200, message, data);
        return new ResponseEntity<>(ok, HttpStatus.OK);
    }

    public static ResponseEntity<ResponseObjectResult> badRequest(String message) {
        ResponseObjectResult badRequest = new ResponseObjectResult(false, 400, message, null);
        return new ResponseEntity<>(badRequest, HttpStatus.BAD_REQUEST);
    }
}
