package demo.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class IndexController {

    @Value("${info:test}")
    private String nacosCongig;

    @GetMapping("/config")
    public String test(){
        return nacosCongig;
    }
}
