package com.codeHotelSoft.HotelSotfServer.utill;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


import java.util.Date;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.Signature;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component



public class JwtUtil {


    private String generateToken(Map<String, Object> extraClaims, UserDetails details){
          return Jwts.builder().setClaims(extraClaims).setSubject(details.getUsername())
                  .setIssuedAt(new Date(System.currentTimeMillis()))
                  .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*24))
                  .signWith(SignatureAlgorithm.HS256, getSigningKey()).compact();

    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);

    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username =  extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);


    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject); // usar extractClaim
    }

    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration); // usar extractClaim
    }


    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);

    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    private Key getSigningKey() {
        byte [] keyBytes = Decoders.BASE64.decode( "413F4428472BB6250655368566D5970337336763979244226452948404D6351");
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
