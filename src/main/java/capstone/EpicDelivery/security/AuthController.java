package capstone.EpicDelivery.security;

import capstone.EpicDelivery.Users.User;
import capstone.EpicDelivery.Users.UsersService;
import capstone.EpicDelivery.Users.payloads.UserRequestPayload;
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
        body.setCreditCard("1234123412341234");
        User created = usersService.create(body);

        return created;
    }

    @PostMapping("/login")
    public LoginSuccessfullPayload login(@RequestBody UserLoginPayload body) {
        // 1. Verifichiamo che l'email dell'utente sia presente nel db

        User user = usersService.findByEmail(body.getEmail());

        // 2. In caso affermativo, devo verificare che la pw corrisponda a quella
        // trovata nel db
        if (bcrypt.matches(body.getPassword(), user.getPassword())) {

            // 3. Se le credenziali sono OK --> genero un JWT e lo invio come risposta
            String token = jwtTools.createToken(user);
            return new LoginSuccessfullPayload(token);

        } else {
            // 4. Se le credenziali NON sono OK --> 401
            throw new UnauthorizedException("Credenziali non valide!");
        }
    }

}
