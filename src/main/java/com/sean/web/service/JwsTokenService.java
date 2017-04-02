package com.sean.web.service;

import com.sean.web.config.JwtConfiguration;
import com.sean.web.config.JwtConstants;
import com.sean.web.model.AccountDetails;
import com.sean.web.model.token.JwtToken;
import com.sean.web.model.token.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
/**
 * @author Sean
 *
 * A dedicated {@link Service} for the validating and generating of both access and refresh tokens using {@link io.jsonwebtoken.Jwt}.
 */
public class JwsTokenService {

    /**
     * The settings of the {@link JwtConfiguration}.
     */
    private final JwtConfiguration settings;

    /**
     * The current {@link DateTime}.
     */
    private final DateTime currentTime = new DateTime();

    @Autowired
    public JwsTokenService(JwtConfiguration settings) {
        this.settings = settings;
    }

    /**
     * Generates an access token from {@link AccountDetails}.
     * @param accountDetails The {@link AccountDetails} used to generate the access token.
     * @return The generated access {@link JwtToken}.
     */
    public Token generateAccessJwtToken(AccountDetails accountDetails) {
        Claims claims = Jwts.claims().setSubject(accountDetails.getUsername());
        claims.put(JwtConstants.CLAIMS_NAME, accountDetails.getAuthorities().stream().map(s -> s.toString()).collect(Collectors.toList()));

        String token = Jwts.builder()
          .setClaims(claims)
          .setIssuer(settings.getOwner())
          .setIssuedAt(currentTime.toDate())
          .setExpiration(currentTime.plusMinutes(settings.getExpiration()).toDate())
          .signWith(SignatureAlgorithm.HS512, settings.getSignatureKey())
        .compact();

        return new JwtToken(token);
    }

    /**
     * Generates an refresh token from {@link AccountDetails}.
     * @param accountDetails The {@link AccountDetails} used to generate the access token.
     * @return The generated refresh {@link JwtToken}.
     */
    public Token createRefreshToken(AccountDetails accountDetails) {
        Claims claims = Jwts.claims().setSubject(accountDetails.getUsername());
        claims.put(JwtConstants.CLAIMS_NAME, accountDetails.getAuthorities().stream().map(s -> s.toString()).collect(Collectors.toList()));
        
        String token = Jwts.builder()
          .setClaims(claims)
          .setIssuer(settings.getOwner())
          .setId(UUID.randomUUID().toString())
          .setIssuedAt(currentTime.toDate())
          .setExpiration(currentTime.plusMinutes(settings.getRefreshExpiration()).toDate())
          .signWith(SignatureAlgorithm.HS512, settings.getSignatureKey())
        .compact();

        return new JwtToken(token);
    }

    /**
     * Transforms a token to a {@link AccountDetails} object.
     * @param token The token.
     * @return A new {@link AccountDetails} based on the information within the token.
     */
    public AccountDetails jwsClaimsToUserContext(String token) {
        Jws<Claims> jwsClaims = settings.parseClaims(token);
        String subject = jwsClaims.getBody().getSubject();
        List<String> scopes = jwsClaims.getBody().get(JwtConstants.CLAIMS_NAME, List.class);
        List<GrantedAuthority> authorities = scopes.stream().map(authority -> new SimpleGrantedAuthority(authority)).collect(Collectors.toList());
        return new AccountDetails(subject, authorities);
    }

    /**
     * Checks to see if a token has expired.
     * @param token The token to check.
     * @return true if expired, false if not.
     */
    public boolean hasTokenExpired(String token) {
        Jws<Claims> jwsClaims = settings.parseClaims(token);
        Claims bodyClaims = jwsClaims.getBody();
        Date expiryDate = bodyClaims.getExpiration();
        boolean hasExpired = expiryDate.after(currentTime.toDate());

        return hasExpired;

    }
}
