package com.healthmonitor.repositories.impl;

import com.healthmonitor.pojo.Package;
import com.healthmonitor.repositories.PackageRepository;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

@Repository
public class PackageRepositoryImpl implements PackageRepository {

    private static final int PAGE_SIZE = 10;

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Package> getPackage(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Package> q = b.createQuery(Package.class);
        Root<Package> root = q.from(Package.class);
        q.orderBy(b.desc(root.get("createdAt")));
        q.select(root);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();
            String kw = params.get("kw");

            if (kw != null && !kw.isEmpty()) {
                Predicate namePredicate = b.like(root.get("name"), "%" + kw + "%");
                Predicate sessionsPredicate = b.like(root.get("sessions"), "%" + kw + "%");
                Predicate pricePredicate = b.like(root.get("price"), "%" + kw + "%");
                Predicate durationPredicate = b.like(root.get("duration"), "%" + kw + "%");
                predicates.add(b.or(namePredicate, sessionsPredicate, pricePredicate, durationPredicate));
            }
            if (!predicates.isEmpty()) {
                q.where(predicates.toArray(new Predicate[0]));
            }
        }

        Query query = s.createQuery(q);

        if (params != null) {
            int page = 1;
            try {
                page = Integer.parseInt(params.getOrDefault("page", "1"));
            } catch (NumberFormatException e) {
                page = 1;
            }
            int start = (page - 1) * PAGE_SIZE;
            query.setFirstResult(start);
            query.setMaxResults(PAGE_SIZE);
        }

        return query.getResultList();
    }

    @Override
    public Package createOrUpdateProgress(Package pkg) {
        Session s = factory.getObject().getCurrentSession();

        if (pkg.getId() == null) {
            s.persist(pkg);
        } else {
            pkg = s.merge(pkg);
        }

        return pkg;
    }

    @Override
    public void deletePackage(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Package p = this.getPackageById(id);
        if (p != null) {
            s.remove(p);
        }
    }

    @Override
    public long countPackage(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Long> q = b.createQuery(Long.class);
        Root<Package> root = q.from(Package.class);
        q.select(b.count(root));

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();
            String kw = params.get("kw");

            if (kw != null && !kw.isEmpty()) {
                Predicate namePredicate = b.like(root.get("name"), "%" + kw + "%");
                Predicate sessionsPredicate = b.like(root.get("sessions"), "%" + kw + "%");
                Predicate pricePredicate = b.like(root.get("price"), "%" + kw + "%");
                Predicate durationPredicate = b.like(root.get("duration"), "%" + kw + "%");
                predicates.add(b.or(namePredicate, sessionsPredicate, pricePredicate, durationPredicate));
            }

            if (!predicates.isEmpty()) {
                q.where(predicates.toArray(new Predicate[0]));
            }
        }

        return s.createQuery(q).getSingleResult();
    }

    @Override
    public Package getPackageById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Package.class, id);
    }

}
