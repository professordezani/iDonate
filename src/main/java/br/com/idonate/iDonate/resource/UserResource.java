package br.com.idonate.iDonate.resource;

import br.com.idonate.iDonate.model.User;
import br.com.idonate.iDonate.model.view.ChangePassword;
import br.com.idonate.iDonate.model.view.ValidationUser;
import br.com.idonate.iDonate.service.UserService;
import br.com.idonate.iDonate.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/v1/user")
public class UserResource {

    @Autowired
    private UserService userService;

    @PutMapping("/validation")
    public ResponseEntity<User> validate(@Valid @RequestBody ValidationUser validationUser) throws InvalidLoginException,
            LoginAlreadyValidatedException, InvalidCodValidationException {
        User updatedUser = userService.validate(validationUser);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @GetMapping("/{login}")
    public ResponseEntity<User> searchByLogin(@PathVariable String login) {
        Optional<User> user = userService.searchLogin(login);
        return (user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()));
    }

    /*@GetMapping("/id/{id}")
    public ResponseEntity<User> searchById(@PathVariable Long id) {
        Optional<User> user = userService.searchId(id);
        return (user.isPresent() ? ResponseEntity.ok(user.get()) : ResponseEntity.notFound().build());
    }*/


    @PutMapping("/resendemail/{id}")
    public ResponseEntity<User> resendEmail(@PathVariable Long id, @Valid @RequestBody User user)
            throws InvalidEmailException, RegisterNotFoundException {
        final User savedUser = userService.edit(id, user);
        try {
            userService.triggerEmail(savedUser);
        } catch (Exception e) {
            userService.updateUnsetEmail(savedUser);
        }
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

    @PutMapping("/changepassword/{id}")
    public ResponseEntity<User> changePassword(@PathVariable Long id, @Valid @RequestBody ChangePassword changePassword)
            throws RegisterNotFoundException, NewAndOldPasswordAlikeException, PasswordOldInvalidException {
        User updatedUser = userService.changePassword(id, changePassword);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

}
