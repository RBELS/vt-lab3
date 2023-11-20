package com.adbs.vtlabs.lab3.service.impl;

import com.adbs.vtlabs.lab3.model.service.User;
import com.adbs.vtlabs.lab3.service.AuthorityService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;

@Service
@Slf4j
public class AuthorityServiceImpl implements AuthorityService {
    @Value("${jwt.ttl:PT30M}")
    private Duration jwtTtl;

    private static final String SECRET_KEY = "SERVER_SECRET_KEY_HERE";

    private final MessageDigest messageDigest;
    private final Base64.Encoder base64Encoder;
    private final RSAPublicKey publicKey;
    private final RSAPrivateKey privateKey;

    public AuthorityServiceImpl() throws Exception {
        this.messageDigest = MessageDigest.getInstance("SHA-256");
        this.base64Encoder = Base64.getEncoder();

        this.publicKey = loadPublicKeyFromResource("rsa/public-key.pem");
        this.privateKey = loadPrivateKeyFromResource("rsa/private-key.pem");
    }

    public String generateUserHash(String username, String password) {
        return base64Encoder.encodeToString(
                messageDigest.digest(String.format("%s:%s:%s", username, SECRET_KEY, password).getBytes(StandardCharsets.UTF_8))
        );
    }

    public String generateUserJwt(User user) {
        Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);
        return JWT.create()
                .withClaim("sub", user.getUserId())
                .withClaim("username", user.getUsername())
                .withIssuer("estore")
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusMillis(jwtTtl.toMillis()))
                .withArrayClaim("authorities", new String[]{"USER"})
                .sign(algorithm);
    }

    public boolean verifyUserJwt(String token) {
        try {
            Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("estore")
                    .build();
            DecodedJWT decodedJwt = verifier.verify(token);
            String jwtPayload = new String(Base64.getDecoder().decode(decodedJwt.getPayload()));
            log.info(jwtPayload);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static RSAPublicKey loadPublicKeyFromResource(String publicKeyResource) throws Exception {
        try (InputStream inputStream = AuthorityServiceImpl.class.getClassLoader().getResourceAsStream(publicKeyResource);
             InputStreamReader reader = new InputStreamReader(inputStream);
             PemReader pemReader = new PemReader(reader)) {
            PemObject pemObject = pemReader.readPemObject();
            byte[] content = pemObject.getContent();
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(content);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        }
    }

    private static RSAPrivateKey loadPrivateKeyFromResource(String privateKeyResource) throws Exception {
        try (InputStream inputStream = AuthorityServiceImpl.class.getClassLoader().getResourceAsStream(privateKeyResource);
             InputStreamReader reader = new InputStreamReader(inputStream);
             PemReader pemReader = new PemReader(reader)) {
            PemObject pemObject = pemReader.readPemObject();
            byte[] content = pemObject.getContent();
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(content);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        }
    }
}
