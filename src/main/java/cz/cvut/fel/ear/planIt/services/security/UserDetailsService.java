package cz.cvut.fel.ear.planIt.services.security;

import cz.cvut.fel.ear.planIt.dao.UserDao;
import cz.cvut.fel.ear.planIt.model.User;
import cz.cvut.fel.ear.planIt.security.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserDao userDao;

    /**
     * Instantiates a new User details service.
     *
     * @param userDao the user dao
     */
    @Autowired
    public UserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        final User user = userDao.findByUsername(s);

        if (user == null) {
            throw new UsernameNotFoundException("User with name " + s + " was not found!");
        }

        return new UserDetails(user);
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> permissions) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String permission : permissions) {
            authorities.add(new SimpleGrantedAuthority(permission));
        }
        return authorities;
    }
}
