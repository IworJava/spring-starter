package com.iwor.spring.service;

import com.iwor.spring.database.entity.User;
import com.iwor.spring.database.repository.UserRepository;
import com.iwor.spring.dto.PageResponse;
import com.iwor.spring.dto.UserCreatEditDto;
import com.iwor.spring.dto.UserFilter;
import com.iwor.spring.dto.UserReadDto;
import com.iwor.spring.mapper.UserCreateEditMapper;
import com.iwor.spring.mapper.UserReadMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserCreateEditMapper userCreateEditMapper;
    private final ImageService imageService;

    public List<UserReadDto> findAll() {
        return userRepository.findAllByOrderById().stream()
                .map(userReadMapper::map)
                .toList();
    }

    public List<UserReadDto> findAll(UserFilter filter) {
        return userRepository.findAllByFilter(filter).stream()
                .map(userReadMapper::map)
                .toList();
    }

    public PageResponse<UserReadDto> findAll(UserFilter filter, Pageable pageable) {
        return userReadMapper.map(userRepository.findAllByFilter(filter, pageable));
    }

    public Optional<UserReadDto> findById(Long id) {
        return userRepository.findById(id)
                .map(userReadMapper::map);
    }

    public Optional<byte[]> findAvatar(Long id) {
        return userRepository.findById(id)
                .map(User::getImage)
                .filter(Predicate.not(String::isBlank))
                .flatMap(imageService::download);
    }

    @Transactional
    public UserReadDto create(UserCreatEditDto dto) {
        return Optional.of(dto)
                .map(obj -> {
                    uploadImg(obj.getImage());
                    return userCreateEditMapper.map(obj);
                })
                .map(userRepository::save)
                .map(userReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<UserReadDto> update(Long id, UserCreatEditDto dto) {
        return userRepository.findById(id)
                .map(obj -> {
                    uploadImg(dto.getImage());
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
    private void uploadImg(MultipartFile image) {
        if (!image.isEmpty()) {
            imageService.upload(image.getOriginalFilename(), image.getInputStream());
        }
    }
}
