package com.ike.ChangeMaker;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChangeMakerController {

    @RequestMapping(value = "/changeMaker", produces = "text/plain")
    public String changeMaker(@RequestParam(value="amount", defaultValue = "1.00") String amount,
                              @RequestParam(value="coins", defaultValue = "") String[] coins) {

        return new ChangeMaker(amount, coins).getChange();
    }
}
