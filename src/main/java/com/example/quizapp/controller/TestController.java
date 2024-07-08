package com.example.quizapp.controller;

import com.example.quizapp.model.Custom;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private Custom custom;

    @Value("${custom.my1}")
    private String my1;

    @PostMapping
    @ResponseStatus(value = HttpStatus.IM_USED)
    public Integer pm() {
        return 1;
    }

    @PostMapping(path = "/a")
    @ResponseStatus(value = HttpStatus.BAD_GATEWAY)
    public Integer pm2() {
        return 2;
    }

    @GetMapping(path = "/conf")
    public Custom getConf() {
        /**
         * PRACA DOMOWA --->
         * 1) Stwórz jakis obiekt/klase z miejscem dla twoich propertisow
         * 2) Stwórz propertisy w application.yml - application-prod.yml (różne typy)
         * 3) zbuduj i zwróc taki obiekt
         *
         * custom.my1=Robert Lewandowski
         * custom.flag=false
         * custom.number=12
         *
         * class Custom {
         *   private String my1;
         *   private boolean flag;
         *   ...
         * }
         *
         */

        return custom;
    }

    @GetMapping
    public ResponseEntity<Integer> gm() {
//        var responseEntity = new ResponseEntity<>(3, HttpStatus.ACCEPTED);

        val re = ResponseEntity.status(900).header("app", "moja-aplikacje").body(4);

        return re;
    }

    @GetMapping("/{resultCode}/{number}")
    public ResponseEntity<Integer> gm2(@PathVariable Integer resultCode, @PathVariable(value = "number", required = false) Integer numerek) {
        return ResponseEntity.status(resultCode).body(numerek);
    }
}
