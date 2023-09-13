package capstone.EpicDelivery.Users;


import capstone.EpicDelivery.Users.payloads.UserRequestPayload;
import capstone.EpicDelivery.enums.Role;
import capstone.EpicDelivery.exceptions.BadRequestException;
import capstone.EpicDelivery.exceptions.NotFoundException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsersService {
    private final UsersRepository usersRepo;

    @Autowired
    public UsersService(UsersRepository usersRepo) {
        this.usersRepo = usersRepo;
    }


    public Optional<User> getUsersByRole(Role role) {
        return usersRepo.findByRole(role);
    }

    public User createAdminIfNotExists() {
        Optional<User> adminOptional = usersRepo.findByRole(Role.ADMIN);

        if (adminOptional.isPresent()) {
            return adminOptional.get();
        } else {
            User admin = new User("Admin", "Admin", "Admin Address", "admin@example.com", "admin", "adminpassword");
            admin.setRole(Role.ADMIN);
            return usersRepo.save(admin);
        }
    }

    public User create(UserRequestPayload body){
        usersRepo.findByEmail(body.getEmail()).ifPresent(user -> {
            throw new BadRequestException("L'email è stata utilizzata");
        });
        User newUser = new User(body.getName(), body.getSurname(), body.getAddress(), body.getEmail(), body.getTel(), body.getPassword());
        return usersRepo.save(newUser);
    }

    public Page<User> find(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return usersRepo.findAll(pageable);
    }

    public User findById(UUID id) throws NotFoundException {
        return usersRepo.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public User findByIdAndUpdate(UUID id, UserRequestPayload body) throws NotFoundException {
        User found = this.findById(id);
        found.setEmail(body.getEmail());
        found.setName(body.getName());
        found.setSurname(body.getSurname());

        return usersRepo.save(found);
    }

    public void findByIdAndDelete(UUID id) throws NotFoundException {
        User found = this.findById(id);
        usersRepo.delete(found);
    }

    public User findByEmail(String email) {
        return usersRepo.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Utente con email " + email + " non trovato"));
    }

}
