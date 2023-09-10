package capstone.EpicDelivery.Users.payloads;

import lombok.Getter;

@Getter
public class UserLoginPayload {
    String email;
    String password;
}
