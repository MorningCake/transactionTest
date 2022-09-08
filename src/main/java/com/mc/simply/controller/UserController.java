package com.mc.simply.controller;

import com.mc.simply.dto.UserDto;
import com.mc.simply.model.User;
import com.mc.simply.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.websocket.server.PathParam;
import java.util.List;
import java.util.UUID;

@RestController("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<User>> findAll() {
        List<User> found = service.findAll();
        return ResponseEntity.ok(found);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<User> find(@PathVariable("id") @NotNull UUID id) {
        User found = service.findById(id);
        return ResponseEntity.ok(found);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<User> create(@RequestBody @NotNull @Valid UserDto dto) {
        User saved = service.save(User.builder().name(dto.getName()).build());
        return ResponseEntity.ok(saved);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(
            @PathVariable("id") @NotNull UUID id,
            @RequestBody @NotNull @Valid UserDto dto
    ) {
        service.update(User.builder().id(id).name(dto.getName()).build());
    }

    @PutMapping(path = "/{id}/sender_exception/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateWithSenderExceptionNonTransactional(
            @PathVariable("id") @NotNull UUID id,
            @RequestBody @NotNull @Valid UserDto dto
    ) {
        service.updateWithSenderExceptionNonTransactional(User.builder().id(id).name(dto.getName()).build());
    }

    @PutMapping(path = "/{id}/sender_exception/transactional", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateWithSenderExceptionTransactional(
            @PathVariable("id") @NotNull UUID id,
            @RequestBody @NotNull @Valid UserDto dto
    ) {
        service.updateWithSenderExceptionTransactional(User.builder().id(id).name(dto.getName()).build());
    }

    @PutMapping(path = "/{id}/sender_exception/transactional/sender_required_new_tr", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateWithRequiredNewTrSenderExceptionTransactional(
            @PathVariable("id") @NotNull UUID id,
            @RequestBody @NotNull @Valid UserDto dto
    ) {
        service.updateWithRequiredNewTrSenderExceptionTransactional(User.builder().id(id).name(dto.getName()).build());
    }

    @DeleteMapping(path = "/{id}")
    public void findById(@PathParam("id") @NotNull UUID id) {
        service.delete(id);
    }
}
