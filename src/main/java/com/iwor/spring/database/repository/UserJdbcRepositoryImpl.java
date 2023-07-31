package com.iwor.spring.database.repository;

import com.iwor.spring.database.entity.Role;
import com.iwor.spring.database.entity.User;
import com.iwor.spring.dto.PersonalInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class UserJdbcRepositoryImpl implements UserJdbcRepository {

    private static final String FIND_BY_COMPANY_ID_AND_ROLE_SQL = """
            SELECT 
                firstname,
                lastname,
                birth_date
            FROM spring.users
            WHERE company_id = ? AND role = ?
            """;
    private static final String UPDATE_COMPANY_AND_ROLE_SQL = """
            UPDATE spring.users
            SET company_id = ?,
                role = ?
            WHERE id = ?
            """;
    private static final String UPDATE_COMPANY_AND_ROLE_NAMED_SQL = """
            UPDATE spring.users
            SET company_id = :company_id,
                role = :role
            WHERE id = :id
            """;

    private static final String FIND_ALL_FETCH_SQL = """
            SELECT * FROM spring.users;
            """;

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<PersonalInfo> findAllByCompanyIdAndRole(Integer companyId, Role role) {
        return jdbcTemplate.query(
                FIND_BY_COMPANY_ID_AND_ROLE_SQL,
                (rs, rowNum) -> new PersonalInfo(
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        Optional.ofNullable(rs.getDate("birth_date"))
                                .map(Date::toLocalDate)
                                .orElse(null)
                ),
                companyId, role.name());
    }

    @Override
    public int[] updateCompanyAndRole(List<User> users) {
        var params = users.stream()
                .map(user -> new Object[]{
                        user.getCompany().getId(),
                        user.getRole().name(),
                        user.getId()
                }).toList();
        return jdbcTemplate.batchUpdate(UPDATE_COMPANY_AND_ROLE_SQL, params);
    }

    @Override
    public int[] updateCompanyAndRoleNamed(List<User> users) {
        var params = users.stream()
                .map(user -> Map.of(
                        "company_id", user.getCompany().getId(),
                        "role", user.getRole().name(),
                        "id", user.getId()
                ))
                .map(MapSqlParameterSource::new)
                .toArray(MapSqlParameterSource[]::new);
        return namedParameterJdbcTemplate.batchUpdate(UPDATE_COMPANY_AND_ROLE_NAMED_SQL, params);
    }
}
