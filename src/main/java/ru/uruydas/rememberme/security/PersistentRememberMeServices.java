package ru.uruydas.rememberme.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import ru.uruydas.rememberme.dao.PersistentUserSessionDao;
import ru.uruydas.rememberme.model.PersistentUserSession;
import ru.uruydas.users.model.User;
import ru.uruydas.users.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;

public class PersistentRememberMeServices implements RememberMeServices {
    private static final String TOKEN_HEADER_NAME = "X-Remember-Me-Token";

    @Autowired
    private PersistentUserSessionDao persistentUserSessionDao;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication autoLogin(HttpServletRequest request, HttpServletResponse response) {
        Optional<RememberMeAuthentication> auth = Optional.ofNullable(request.getHeader(TOKEN_HEADER_NAME))
                .map(persistentUserSessionDao::findByToken)
                .map(RememberMeAuthentication::new);

        if (auth.isPresent()) {
            return auth.get();
        }

        return null;
    }

    @Override
    public void loginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) {
        User currentUser = userService.getCurrentUser();

        String header = createNewToken(currentUser);
        persistentUserSessionDao.save(new PersistentUserSession(header, currentUser.getId()));

        response.setHeader(TOKEN_HEADER_NAME, header);
    }

    private String createNewToken(User currentUser) {
        return passwordEncoder.encode(currentUser.getId() + ":" + LocalDateTime.now(Clock.systemUTC()));
    }

    @Override
    public void loginFail(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader(TOKEN_HEADER_NAME);

        if (token != null) {
            persistentUserSessionDao.deleteByToken(token);
        }
    }
}
