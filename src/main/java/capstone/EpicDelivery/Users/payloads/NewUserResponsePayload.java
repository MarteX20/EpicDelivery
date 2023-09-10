package capstone.EpicDelivery.Users.payloads;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@AllArgsConstructor
@ToString
public class NewUserResponsePayload {
    private UUID id;
}
