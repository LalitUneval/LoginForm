package com.lalit.securityDemo.service;

import com.lalit.securityDemo.model.Users;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.websocket.Decoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    private String secretKey=null;

    //instead of using the hardCode value for the key secrete key generation we can generate it
    /*
         public JwtService()  {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");//
            SecretKey sk = keyGenerator.generateKey();
            secretKey=Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

     */


    //step 2 generate the token
    public String generateToken(Users users) {
        //creating the map for the add
        Map<String , Object> claims = new HashMap<>();

        return Jwts
                .builder()
                .claims()
                .add(claims)//it take the map so we need to create the map
                .subject(users.getUsername())
                .issuer("LUC")//who isseure this toke Lalit uneval coder
                .issuedAt(new Date(System.currentTimeMillis()))//when it was issed
                .expiration(new Date(System.currentTimeMillis()+ 60*10*1000))// when it was exp after the generation of 10 min
                .and()
                .signWith(generateKey())//this is the signature section
                .compact();
    }


    private SecretKey generateKey(){
        byte [] decode
                =Decoders.BASE64.decode(getSecretKey());
        return Keys.hmacShaKeyFor(decode);
    }

    //step 1 we get the secret key
    // this key is based on the base 64 so we want to decode it
    public String getSecretKey(){
        return secretKey="yMSC8Ta3Zo0pIDh0MVVrxghb46ks8pqyxfeuNOnSWlY=";
    }



    /*
        //which we get the secreat key we decode it into base 64
    private SecretKey generateKey() {

        byte[] decode = Decoders.BASE64.decode(secretKey);

        return Keys.hmacShaKeyFor(decode);


    }
    */
    //step 3 for the validation of the jwt token

    public String extractUserName(String token) {
        return Jwts
                .parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validToken(String token, UserDetails userDetails) {
        String username = extractUserName(token);
        Date expirationDate = Jwts
                .parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();

        return username.equals(userDetails.getUsername()) && !expirationDate.before(new Date());
    }
}
