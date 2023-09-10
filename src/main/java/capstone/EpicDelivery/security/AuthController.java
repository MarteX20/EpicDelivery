package capstone.EpicDelivery.security;

import capstone.EpicDelivery.Users.User;
import capstone.EpicDelivery.Users.UsersService;
import capstone.EpicDelivery.Users.payloads.LoginSuccessfullPayload;
import capstone.EpicDelivery.Users.payloads.UserLoginPayload;
import capstone.EpicDelivery.Users.payloads.UserRequestPayload;
import capstone.EpicDelivery.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    UsersService usersService;

    @Autowired
    JWTTools jwtTools;

    @Autowired
    PasswordEncoder bcrypt;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User saveUser(@RequestBody @Validated UserRequestPayload body) {
        body.setPassword(bcrypt.encode(body.getPassword()));
        User created = usersService.create(body);

        return created;
    }

    @PostMapping("/login")
    public LoginSuccessfullPayload login(@RequestBody UserLoginPayload body) {
        User user = usersService.findByEmail(body.getEmail());

        if (bcrypt.matches(body.getPassword(), user.getPassword())) {
            String token = jwtTools.createToken(user);
            return new LoginSuccessfullPayload(token);

        } else {
            throw new UnauthorizedException("Credenziali non valide!");
        }
    }

}
