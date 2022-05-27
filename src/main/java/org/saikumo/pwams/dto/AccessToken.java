package org.saikumo.pwams.dto;

import lombok.Builder;
import lombok.Data;
import org.saikumo.pwams.entity.Role;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class AccessToken {
    private String loginAccount;
    private Collection<? extends GrantedAuthority> authorities;
    private String token;
    private Date expirationTime;
}
