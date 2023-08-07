package com.iwor.spring.database.repository;

import com.iwor.spring.database.entity.User;
import com.iwor.spring.dto.PageResponse;
import com.iwor.spring.dto.UserFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

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
        var typedQuery = getUserTypedQuery(filter);
        return typedQuery.getResultList();
    }

    @Override
    public PageResponse<User> findAllByFilter(UserFilter filter, Pageable pageable) {
        var pageNumber = pageable.getPageNumber();
        var pageSize = pageable.getPageSize();
        var users = getUserTypedQuery(filter)
                .setMaxResults(pageSize)
                .setFirstResult(pageNumber * pageSize)
                .getResultList();

        var cb = em.getCriteriaBuilder();
        var countQuery = cb.createQuery(Long.class);
        countQuery.select(cb.count(countQuery.from(User.class)));
        var totalElements = em.createQuery(countQuery).getSingleResult();

        return PageResponse.of(
                users,
                pageNumber,
                pageSize,
                totalElements
        );
    }

    private TypedQuery<User> getUserTypedQuery(UserFilter filter) {
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

        return em.createQuery(criteriaQuery);
    }
}
