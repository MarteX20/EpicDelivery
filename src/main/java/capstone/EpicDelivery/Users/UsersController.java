package capstone.EpicDelivery.Users;

import capstone.EpicDelivery.Users.payloads.UserRequestPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UsersController {
    private final UsersService usersServ;

    @Autowired
    public UsersController(UsersService usersServ) {
        this.usersServ = usersServ;
    }

    @GetMapping
    @PreAuthorize("hasAutority('ADMIN')")
    public Page<User> getUsers(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "id") String sortBy){
        return usersServ.find(page, size, sortBy);
    }

    @GetMapping("/{userId}")
    public User findById(@PathVariable UUID userId) {
        return usersServ.findById(userId);
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathVariable UUID userId, @RequestBody UserRequestPayload body) {
        return usersServ.findByIdAndUpdate(userId, body);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable UUID userId) {
        usersServ.findByIdAndDelete(userId);
    }
}
