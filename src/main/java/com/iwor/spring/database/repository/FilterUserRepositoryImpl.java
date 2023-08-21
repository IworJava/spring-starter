package com.iwor.spring.database.repository;

import com.iwor.spring.database.entity.User;
import com.iwor.spring.dto.PageResponse;
import com.iwor.spring.dto.UserFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.hibernate.jpa.SpecHints;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.query.QueryUtils;

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
        var criteriaQuery = getUserCriteriaQuery(filter, Pageable.unpaged());
        var typedQuery = em.createQuery(criteriaQuery);
        typedQuery.setHint(SpecHints.HINT_SPEC_LOAD_GRAPH, withCompany());
        return typedQuery.getResultList();
    }

    @Override
    public PageResponse<User> findAllByFilter(UserFilter filter, Pageable pageable) {
        var pageNumber = pageable.getPageNumber();
        var pageSize = pageable.getPageSize();

        var typedQuery = getUserCriteriaQuery(filter, pageable);
        var users = em.createQuery(typedQuery)
                .setMaxResults(pageSize)
                .setFirstResult(pageNumber * pageSize)
                .setHint(SpecHints.HINT_SPEC_LOAD_GRAPH, withCompany())
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

    private Object withCompany() {
        var entityGraph = em.createEntityGraph(User.class);
        entityGraph.addAttributeNodes("company");
        return entityGraph;
    }

    private CriteriaQuery<User> getUserCriteriaQuery(UserFilter filter, Pageable pageable) {
        var cb = em.getCriteriaBuilder();
        var criteriaQuery = cb.createQuery(User.class);

        var root = criteriaQuery.from(User.class);

        List<Predicate> predicates = new ArrayList<>();
        if (filter.firstname() != null) {
            predicates.add(cb.like(cb.lower(root.get("firstname")), "%" + filter.firstname().toLowerCase() + "%"));
        }
        if (filter.lastname() != null) {
            predicates.add(cb.like(cb.lower(root.get("lastname")), "%" + filter.lastname().toLowerCase() + "%"));
        }
        if (filter.birthDate() != null) {
            predicates.add(cb.lessThan(root.get("birthDate"), filter.birthDate()));
        }

        criteriaQuery.select(root)
                .where(predicates.toArray(Predicate[]::new))
                .orderBy(QueryUtils.toOrders(
                        pageable.getSort().isEmpty()
                                ? Sort.by("id")
                                : pageable.getSort(),
                        root, cb));

        return criteriaQuery;
    }
}
