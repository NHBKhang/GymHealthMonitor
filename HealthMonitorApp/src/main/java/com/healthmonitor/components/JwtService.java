package com.healthmonitor.components;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Base64;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JwtService {

    private static final String SECRET_KEY = Base64.getEncoder()
            .encodeToString("JANCAKE9*@Z!BN!1naan1LU12UJP~R".getBytes(StandardCharsets.UTF_8));
    private static final byte[] SHARED_SECRET_KEY = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
    public static final int EXPIRE_TIME = 86400000;

    //Tạo JWT từ username
    public String generateTokenLogin(String username) {
        String token = null;
        try {
            JWSSigner signer = new MACSigner(SHARED_SECRET_KEY);

            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(username) // Dùng subject để định danh
                    .issueTime(new Date()) // Thời gian phát hành
                    .expirationTime(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                    .build();

            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
            signedJWT.sign(signer);

            token = signedJWT.serialize();
        } catch (JOSEException e) {
            System.out.println(e.getMessage());
        }

        return token;
    }

    //Giải mã và lấy thông tin từ JWT
    private JWTClaimsSet getClaimsFromToken(String token) {
        JWTClaimsSet claims = null;
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(SHARED_SECRET_KEY);

            if (signedJWT.verify(verifier)) {
                claims = signedJWT.getJWTClaimsSet();
            }
        } catch (JOSEException | ParseException e) {
            System.err.println(e.getMessage());
        }
        return claims;
    }

    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    private Date getExpirationDateFromToken(String token) {
        JWTClaimsSet claims = getClaimsFromToken(token);
        Date expiration = claims.getExpirationTime();
        return expiration;
    }

    public boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    public boolean validateTokenLogin(String token) {
        if (token == null || token.trim().isEmpty()) {
            return false;
        }
        try {
            String username = getUsernameFromToken(token);
            return !(username == null || username.isEmpty() || isTokenExpired(token));
        } catch (Exception e) {
            return false;
        }
    }
}
