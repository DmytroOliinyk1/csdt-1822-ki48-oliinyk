package com.lpnu.virtual.library.core.user.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "user_id")
        private Long id;

        @Column(name = "login")
        private String login;

        @Column(name = "first_name")
        private String firstName;

        @Column(name = "last_name")
        private String lastName;

        @Column(name = "password")
        private String password;

        @Column(name = "enabled")
        private Boolean enabled;

        @ToString.Exclude
        @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
        private Set<Role> roles;

        @Override
        public boolean equals(Object o) {
                if (this == o)
                        return true;
                if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
                        return false;
                User user = (User) o;
                return id != null && Objects.equals(id, user.id);
        }

        @Override
        public int hashCode() {
                return getClass().hashCode();
        }
}
