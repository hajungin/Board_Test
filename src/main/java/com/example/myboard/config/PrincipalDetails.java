package com.example.myboard.config;

import com.example.myboard.entity.UserAccount;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class PrincipalDetails implements UserDetails {

    private UserAccount user;

    public PrincipalDetails(UserAccount user) {
        this.user = user;
    }

    public UserAccount getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect =  new ArrayList<>();
        collect.add( ()-> {
            return user.getUserRole().getValue();});
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getUserPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
//    만료된 계정인지 체크해준다

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
//    계정 잠금

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
//    계정이 만료되었는지?

    @Override
    public boolean isEnabled() {
        return true;
//        false 가 만료되지 않은 것 기본 값은 만료된 것
//        사용가능하려면 true 로 바꿔주는 작업
    }
//
}
