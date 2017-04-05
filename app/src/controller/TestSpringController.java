package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/testController")
public class TestSpringController {
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView helloWorldRequest() {
        String message = "<h1>Success</h1>";
        return new ModelAndView("welcome", "message", message);
    }
}
