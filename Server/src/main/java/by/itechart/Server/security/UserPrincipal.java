package by.itechart.Server.security;

import by.itechart.Server.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {
    private int id;

    private String name;

    private String login;

    @JsonIgnore
    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> roles;

    private Integer clientCompanyId;


    public UserPrincipal(int id, String name, String login, String email, String password,
                         Collection<? extends GrantedAuthority> authorities, Integer clientCompanyId) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.email = email;
        this.password = password;
        this.roles = authorities;
        this.clientCompanyId=clientCompanyId;
    }

    public static UserPrincipal create(User user) {
//        List<GrantedAuthority> authorities = Arrays.asList(user.getRoles())
//                new SimpleGrantedAuthority(user.getRoles().toString()));

        List <GrantedAuthority> authorities=user.getRoles().stream()
                .map(role->new SimpleGrantedAuthority(role.toString())).collect(Collectors.toList());

        Integer clientCompanyId=null;
        if(user.getClientCompany()!=null){
            clientCompanyId=user.getClientCompany().getId();
        }
        return new UserPrincipal(
                user.getId(),
                user.getName(),
                user.getLogin(),
                user.getEmail(),
                user.getPassword(),
                authorities,
                clientCompanyId
        );
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    public Integer getClientCompanyId(){
        return clientCompanyId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}

