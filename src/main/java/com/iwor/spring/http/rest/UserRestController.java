package com.iwor.spring.http.rest;

import com.iwor.spring.dto.PageResponse;
import com.iwor.spring.dto.UserCreateEditDto;
import com.iwor.spring.dto.UserFilter;
import com.iwor.spring.dto.UserReadDto;
import com.iwor.spring.service.UserService;
import com.iwor.spring.validation.group.Creation;
import com.iwor.spring.validation.group.Update;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserRestController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<PageResponse<UserReadDto>> findAll(UserFilter filter,
                                             Pageable pageable) {
        return Optional.of(userService.findAll(filter, pageable))
                .map(content -> ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .body(content))
                .orElseGet(notFound()::build);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserReadDto> findById(@PathVariable Long id) {
        return userService.findById(id)
                .map(content -> ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .body(content))
                .orElseGet(notFound()::build);
    }

//    @GetMapping("/{id}/avatar")
//    public ResponseEntity<byte[]> findAvatar(@PathVariable Long id) {
//        return userService.findAvatar(id)
//                .map(content -> ok()
//                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
//                        .contentLength(content.length)
//                        .body(content))
//                .orElseGet(notFound()::build);
//    }

    @GetMapping("/{id}/avatar")
    public ResponseEntity<byte[]> findAvatar(@PathVariable Long id,
                                             @RequestParam("name") String imageName) {
        return userService.findAvatar(id, imageName)
                .map(content -> ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                        .contentLength(content.length)
                        .body(content))
                .orElseGet(notFound()::build);
    }

    @PostMapping
    public ResponseEntity<UserReadDto> create(@RequestBody
                                              @Validated({Default.class, Creation.class})
                                              UserCreateEditDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(userService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserReadDto> update(@PathVariable Long id,
                              @RequestBody
                              @Validated({Default.class, Update.class})
                              UserCreateEditDto dto) {
        return userService.update(id, dto)
                .map(content -> ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .body(content))
                .orElseGet(notFound()::build);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return userService.delete(id)
                ? noContent().build()
                : notFound().build();
    }
}
