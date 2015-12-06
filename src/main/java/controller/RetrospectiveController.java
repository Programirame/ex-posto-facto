package controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RetrospectiveController {

    public ResponseEntity<?> createRetrospective() {
        return new ResponseEntity<Object>(null, HttpStatus.OK);
    }

}
