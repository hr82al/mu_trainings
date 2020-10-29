package ru.haval.muTrainings.accessingdatajpa;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class EmployeeSpecifications{
/*
    public static Specification<Employee> isUser_delEmployee() {
        return new Specification<Employee>() {
            public Predicate toPredicate(Root<T> root, CriteriaQuery query, CriteriaBuilder cb) {
                return cb.equal(root.get("user_del"), 0);
            }
        };
    }*/
    public static Specification isUser_del() {
        return new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
                CriteriaQuery<Employee> q = criteriaBuilder.createQuery(Employee.class);
                root = query.from(Employee.class);
                return criteriaBuilder.equal(root.get("user_del"), 0);
            }
        };
    }
}
