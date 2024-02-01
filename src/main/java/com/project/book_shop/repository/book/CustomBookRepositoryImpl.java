package com.project.book_shop.repository.book;

import com.project.book_shop.DTO.BookFilter;
import com.project.book_shop.entity.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomBookRepositoryImpl implements CustomBookRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Book> findByFilter(BookFilter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> query = cb.createQuery(Book.class);
        Root<Book> root = query.from(Book.class);

        List<Predicate> predicates = new ArrayList<>();

        // Добавляем условия фильтрации в зависимости от ненулевых полей в filter
        if (filter.getName() != null && !filter.getName().isEmpty()) {
            predicates.add(cb.like(root.get("name"), "%" + filter.getName() + "%"));
        }

        if (filter.getBrand() != null && !filter.getBrand().isEmpty()) {
            predicates.add(cb.equal(root.get("brand"), filter.getBrand()));
        }

        // Здесь можно и возможно нужно добавить здесь условия для других полей фильтрации

        query.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(query).getResultList();
    }
}
