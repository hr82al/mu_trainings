package ru.haval.muTrainings.authentication;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserDetails implements UserDetails {

  private static final long serialVersionUID = -3040015349568642659L;
  private User user;

  public MyUserDetails(User user) {
    this.user = user;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
    return Arrays.asList(authority);
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return user.getEnabled();
  }

  @Override
  public boolean isAccountNonLocked() {
    return user.getEnabled();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return user.getEnabled();
  }

  @Override
  public boolean isEnabled() {
    return user.getEnabled();
  }
}
