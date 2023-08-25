package com.iwor.spring.service;

import com.iwor.spring.database.entity.User;
import com.iwor.spring.database.repository.UserRepository;
import com.iwor.spring.dto.PageResponse;
import com.iwor.spring.dto.UserCreateEditDto;
import com.iwor.spring.dto.UserFilter;
import com.iwor.spring.dto.UserReadDto;
import com.iwor.spring.mapper.UserCreateEditMapper;
import com.iwor.spring.mapper.UserReadMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserCreateEditMapper userCreateEditMapper;
    private final ImageService imageService;

    public List<UserReadDto> findAll() {
        return userRepository.findAllByOrderById().stream()
                .map(userReadMapper::map)
                .toList();
    }

    @PostFilter("filterObject.role.equals('ADMIN')")
    public List<UserReadDto> findAll(UserFilter filter) {
        return userRepository.findAllByFilter(filter).stream()
                .map(userReadMapper::map)
                .toList();
    }

    public PageResponse<UserReadDto> findAll(UserFilter filter, Pageable pageable) {
        return userReadMapper.map(userRepository.findAllByFilter(filter, pageable));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public Optional<UserReadDto> findById(Long id) {
        return userRepository.findById(id)
                .map(userReadMapper::map);
    }

    public Optional<byte[]> findAvatar(Long id) {
        return userRepository.findById(id)
                .map(User::getImage)
                .flatMap(img -> findAvatar(id, img));
    }

    public Optional<byte[]> findAvatar(Long id, String imageName) {
        return Optional.ofNullable(imageName)
                .filter(Predicate.not(String::isBlank))
                .map(img -> getFullImageName(id, img))
                .flatMap(imageService::download);
    }

    @Transactional
    public UserReadDto create(UserCreateEditDto dto) {
        return Optional.of(dto)
                .map(userCreateEditMapper::map)
                .map(userRepository::save)
                .map(entity -> {
                    uploadImg(entity.getId(), dto.getImage());
                    return userReadMapper.map(entity);
                })
                .orElseThrow();
    }

    @Transactional
    public Optional<UserReadDto> update(Long id, UserCreateEditDto dto) {
        return userRepository.findById(id)
                .map(obj -> {
                    uploadImg(id, dto.getImage());
                    return userCreateEditMapper.map(dto, obj);
                })
                .map(userRepository::saveAndFlush)
                .map(userReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    userRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    @SneakyThrows
    private void uploadImg(Long id, MultipartFile image) {
        if (image != null && !image.isEmpty()) {
            imageService.upload(
                    getFullImageName(id, image.getOriginalFilename()),
                    image.getInputStream()
            );
        }
    }

    private String getFullImageName(Long id, String img) {
        return String.format("%d-%s", id, img);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        Collections.singleton(user.getRole())
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user: " + username));
    }
}
