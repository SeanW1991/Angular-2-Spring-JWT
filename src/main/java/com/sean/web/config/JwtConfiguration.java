package com.sean.web.config;

import com.sean.web.exceptions.TokenExpiredException;
import io.jsonwebtoken.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;

/**
 * @author Sean
 *
 * The configuration class for the JWT token settings. These settings are defined within the /resources/application.yml file.
 *
 */
@Configuration
@ConfigurationProperties(prefix = JwtConfiguration.JWT_CONFIG_IDENTIFIER)
public class JwtConfiguration {

    /**
     * The id of the jwt config within the application.yml file.
     */
    public  static final String JWT_CONFIG_IDENTIFIER = "jwt_config";

    /**
     * The access token expiration time in minutes.
     */
    private Integer expiration;

    /**
     * The refresh token expiration time in minutes.
     */
    private Integer refreshExpiration;

    /**
     * The owner.
     */
    private String owner;

    /**
     * The secret key.
     */
    private String signatureKey;

    /**
     * Parses a {@param token} to a {@link Jws<Claims>}.
     * @param token The token to decode.
     * @return The decoded token as a {@link Jws<Claims>}.
     */
    public Jws<Claims> parseClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(signatureKey).parseClaimsJws(token);
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException ex) {
            throw new BadCredentialsException("Invalid JWT token: ", ex);
        } catch (ExpiredJwtException expiredEx) {
            throw new TokenExpiredException("JWT Token is expired");
        }
    }

    /**
     * Gets the expiration time of the refresh token.
     * @return The {@code refreshExpiration}.
     */
    public Integer getRefreshExpiration() {
        return refreshExpiration;
    }

    /**
     * Gets the expiration time of the access token.
     * @return The {@code refreshExpiration}.
     */
    public Integer getExpiration() {
        return expiration;
    }

    /**
     * Gets the owner of the website.
     * @return The {@code owner}.
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Gets the signature key.
     * @return The {@code signatureKey}.
     */
    public String getSignatureKey() {
        return signatureKey;
    }

    /**
     * Sets the expiration time of the refresh token.
     * @param refreshExpiration The expiration to set.
     */
    public void setRefreshExpiration(Integer refreshExpiration) {
        this.refreshExpiration = refreshExpiration;
    }

    /**
     * Sets the expiration time of the expiration token.
     * @param expiration The expiration to set.
     */
    public void setExpiration(Integer expiration) {
        this.expiration = expiration;
    }

    /**
     * Sets the signature key.
     * @param signatureKey The key to set.
     */
    public void setSignatureKey(String signatureKey) {
        this.signatureKey = signatureKey;
    }

    /**
     * Sets the owner.
     * @param owner The owner to set.
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }
}
