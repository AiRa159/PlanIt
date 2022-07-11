package cz.cvut.fel.ear.planIt.rest;

import cz.cvut.fel.ear.planIt.model.User;
import cz.cvut.fel.ear.planIt.security.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/login")
public class LoginController extends AbstractController {

    @Autowired
    private AuthenticationProvider provider;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public void login(@RequestBody User user) {
        Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication result = provider.authenticate(auth);
        UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        context.setAuthentication(auth);
    }


    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return null;
    }
}

