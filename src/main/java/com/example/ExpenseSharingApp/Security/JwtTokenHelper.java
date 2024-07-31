package com.example.ExpenseSharingApp.Security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.Signature;
import java.util.Date;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SigningKeyResolverAdapter;

import java.util.function.Function;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import static com.sun.org.apache.xml.internal.security.utils.XMLUtils.getBytes;

//import static java.lang.StringUTF16.getBytes;

@Component
public class JwtTokenHelper {

    public static final long JWT_TOKEN_VALIDITY=5*60*60;
    private final Key key=Keys.secretKeyFor(SignatureAlgorithm.HS512);

    private String secretKey = "afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf";
   SecretKey secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    // SecretKey secret = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    public String getUserNameFromToken(String token){
         return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token){
        return getClaimFromToken(token,Claims::getExpiration);
    }

    public <T>T getClaimFromToken(String token, Function<Claims,T> claimsResolver){
        final Claims claims=getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

   
    private Claims getAllClaimsFromToken(String token){

           return Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token).getBody();
         

    }

    private Boolean isTokenExpired(String token) {

        return getExpirationDateFromToken(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY*1000))
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String extractedUsername = getUserNameFromToken(token);
           System.out.println(extractedUsername);
        return (extractedUsername.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
