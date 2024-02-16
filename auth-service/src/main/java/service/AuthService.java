package service;

import lombok.Getter;
import entity.Role;
import entity.User;
import util.SecurityConstants;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class AuthService {

    @Getter
    private static final AuthService INSTANCE = new AuthService();
    private static final AtomicInteger idCounter = new AtomicInteger();
    private static final Map<Integer, User> userBase = new HashMap<>();
    private static final Map<String, String> tokenBase = new HashMap<>();
    private static final int TOKEN_LENGTH = 32;

    static {
        userBase.put(10, User.builder()
                .name("Pit")
                .password("123")
                .role(Role.ADMIN)
                .build());
    }

    public int registerUser(String name, String password, Role role) {
        int id = idCounter.incrementAndGet();
        User user = new User(name, password, role);
        userBase.put(id, user);
        tokenBase.put(user.getName(), generateToken(id));
        return id;
    }

    public boolean isAuthenticateUser(String name, String password) {
        for (Map.Entry<Integer, User> entry : userBase.entrySet()) {
            User user = entry.getValue();
            if (user.getName().equals(name) && user.getPassword().equals(password)) {
                tokenBase.put(user.getName(), generateToken(entry.getKey()));
                return true;
            }
        }
        return false;
    }
    public String generateToken(Integer id){
        SecureRandom secureRandom = new SecureRandom();
        byte[] tokenBytes = new byte[TOKEN_LENGTH];
        secureRandom.nextBytes(tokenBytes);
        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
        if(isAdmin(id)){
            return token + SecurityConstants.SECRET_WORD;
        }
        return token;
    }
    public boolean isValidateToken(String token) {
        return tokenBase.containsValue(token);
    }

    public String getTokenFromName(String email) {
        for (Map.Entry<String, String> entry : tokenBase.entrySet()) {
            if (entry.getKey().equals(email)) {
                return entry.getValue();
            }
        }
        return null;
    }

    private static boolean isAdmin(Integer id){
        User user = userBase.get(id);
        return user.getRole().equals(Role.ADMIN);
    }

    private AuthService() {

    }
}
