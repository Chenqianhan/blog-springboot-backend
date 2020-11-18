package com.quainhanhub.vue_blog.shiro_jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * There is an exception that Spring Boot Configuration Annotation Processor not configured.
 * It said "The requested URL /spring-boot/docs/2.2.6.RELEASE/reference/html/configuration-metadata.html was not found on this server."
 */

@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "quainhanhub.jwt")
public class JwtUtils {

    private String secret;
    private long expire;
    private String header;
    /**
     * Generate jwt token
     */
    public String generateToken(long userId) {
        Date nowDate = new Date();
        //Timeout
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(userId+"")
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    // Get jwt info
    public Claims getClaimByToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            log.debug("validate is token error ", e);
            return null;
        }
    }

    /**
     * Check if toke expired
     * @return  true：expired
     */
    public boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }
}
