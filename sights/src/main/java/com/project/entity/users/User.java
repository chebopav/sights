package com.project.entity.users;

import com.project.helpers_and_statics.StaticVerifiers;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.URL;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Класс Юзеров
 * Не вижу смысла делить юзеров на обычных и суперюзеров
 */
@Entity
@Table(name = "all_users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;

    @Column(nullable = false, length = 30, unique = true)
    @NotBlank(message = "Логин не может быть пустым")
    @Size(min = 3, max = 30, message = "Не короче 3 и не длиннее 30 симовлов")
    private String login;

    @Column(nullable = false)
    @NotBlank(message = "Пароль не может быть пустым")
    //@Size(min = 8, max = 30, message = "Не короче 8 и не длиннее 30 симовлов")
    private String password;

    @Transient
    private String passwordConfirm;

    @Column(nullable = false, length = 50, unique = true)
    @Email(message = "Некорректный e-mail")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "Имя не может быть пустым")
    private String name;

    private String phone;

    @Transient
    private boolean isOnline;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    @Column(unique = true)
    @URL
    private String photoURL;

    public User() {
    }

    public User(String login, String password, String email, String name) {
        this.setLogin(login);
        this.setPassword(password);
        this.setEmail(email);
        this.setName(name);
    }

    public User(String login, String password, String email, String name, String phone) {
        this.setLogin(login);
        this.setPassword(password);
        this.setEmail(email);
        this.setName(name);
        this.setPhone(phone);
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setLogin(String login) {
        if (login == null || login.trim().length() < 3)
            throw new IllegalArgumentException("Некорректный логин");
        this.login = login;
    }

    public void setPassword(String password) {
        if (!StaticVerifiers.verifiedPass(password)) {
            throw new IllegalArgumentException("Недопустимый пароль");
        }
        this.password = password;
    }

    public void setEmail(String email) {
        if (!StaticVerifiers.verifiedEMail(email))
            throw new IllegalArgumentException("Недопустимый e-mail");
        this.email = email;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public void setName(String name) {
        if (name == null || name.trim().length() < 3)
            throw new IllegalArgumentException("Некорректное имя пользователя");
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
