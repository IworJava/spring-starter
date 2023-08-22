package com.iwor.spring.config;

import com.iwor.spring.database.entity.Role;
import com.iwor.spring.dto.UserCreateEditDto;
import com.iwor.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.lang.reflect.Proxy;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static com.iwor.spring.database.entity.Role.ADMIN;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@RequiredArgsConstructor
@EnableMethodSecurity
@Configuration
public class SecurityConfiguration {

    private final UserService userService;

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        http
//                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(request -> request
                        .requestMatchers(
                                antMatcher("/login"),
                                antMatcher("/users/registration"),
                                antMatcher(HttpMethod.POST, "/users"),
                                antMatcher("/v3/api/docs/**"),
                                antMatcher("/swagger-ui/**")
                        ).permitAll()
                        .requestMatchers(
                                antMatcher("/users/{\\d+}/delete"),
                                mvc.pattern("/admin")
                        ).hasAuthority(ADMIN.getAuthority())
                        .anyRequest().authenticated()
                )
//                .httpBasic(Customizer.withDefaults())
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/users")
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .deleteCookies("JSESSIONID")
                )
                .oauth2Login(config -> config
                        .loginPage("/login")
                        .defaultSuccessUrl("/users")
                        .userInfoEndpoint(userInfo -> userInfo.oidcUserService(oidcUserService()))
                );
        return http.build();
    }

    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        return userRequest -> {
            var idToken = userRequest.getIdToken();
            var userDetails = getUserDetails(idToken);

            var oidcUser = new DefaultOidcUser(userDetails.getAuthorities(), userRequest.getIdToken());
            var userDetailsMethods = Set.of(UserDetails.class.getMethods());

            return (OidcUser) Proxy.newProxyInstance(
                    SecurityConfiguration.class.getClassLoader(),
                    new Class[]{UserDetails.class, OidcUser.class},
                    (proxy, method, args) -> userDetailsMethods.contains(method)
                            ? method.invoke(userDetails, args)
                            : method.invoke(oidcUser, args));
        };
    }

    private UserDetails getUserDetails(OidcIdToken idToken) {
        try {
            return userService.loadUserByUsername(idToken.getEmail());
        } catch (UsernameNotFoundException ignored) {
            userService.create(new UserCreateEditDto(
                    idToken.getEmail(),
                    "123",
                    idToken.getGivenName(),
                    idToken.getFamilyName(),
                    Optional.ofNullable(idToken.getBirthdate())
                            .map(LocalDate::parse)
                            .orElse(null),
                    null,
                    Role.USER,
                    null
            ));
            return userService.loadUserByUsername(idToken.getEmail());
        }
    }
}
