package urujdas.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import urujdas.service.UserService;

@RestController
public class HomeController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "Hello, world!";
    }

    @RequestMapping(value = "/db", method = RequestMethod.GET)
    public String db() {
        return userService.getById(2L).getUsername();
    }

}
