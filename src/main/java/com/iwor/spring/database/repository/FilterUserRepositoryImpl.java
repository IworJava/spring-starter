package com.iwor.spring.database.repository;

import com.iwor.spring.database.entity.User;
import com.iwor.spring.dto.UserFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class FilterUserRepositoryImpl implements FilterUserRepository {

    private final EntityManager em;

//    @Override
//    public List<User> findAllByFilter(UserFilter filter) {
//        var predicate = QPredicates.builder()
//                .add(filter.firstname(), user.firstname::containsIgnoreCase)
//                .add(filter.lastname(), user.lastname::containsIgnoreCase)
//                .add(filter.birthDate(), user.birthDate::before)
//                .buildAnd();
//        return new JPAQuery<User>(em)
//                .select(user)
//                .from(user)
//                .where(predicate)
//                .fetch();
//    }

    @Override
    public List<User> findAllByFilter(UserFilter filter) {
        var cb = em.getCriteriaBuilder();
        var criteriaQuery = cb.createQuery(User.class);

        var user = criteriaQuery.from(User.class);

        List<Predicate> predicates = new ArrayList<>();
        if (filter.firstname() != null) {
            predicates.add(cb.like(cb.lower(user.get("firstname")), "%" + filter.firstname().toLowerCase() + "%"));
        }
        if (filter.lastname() != null) {
            predicates.add(cb.like(cb.lower(user.get("lastname")), "%" + filter.lastname().toLowerCase() + "%"));
        }
        if (filter.birthDate() != null) {
            predicates.add(cb.lessThan(user.get("birthDate"), filter.birthDate()));
        }

        criteriaQuery.select(user)
                .where(predicates.toArray(Predicate[]::new));

        return em.createQuery(criteriaQuery).getResultList();
    }
}
