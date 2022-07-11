package cz.cvut.fel.ear.planIt.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.Collection;

public enum Role {
    ADMIN("ROLE_ADMIN"), MODERATOR("ROLE_MODERATOR"), USER("ROLE_USER");

    private final String name;

    Role(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

