package com.healthmonitor.repositories.impl;

import com.healthmonitor.pojo.Subscription;
import com.healthmonitor.repositories.PackageRepository;
import com.healthmonitor.repositories.SubscriptionRepository;
import com.healthmonitor.repositories.UserRepository;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class SubscriptionRepositoryImpl implements SubscriptionRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PackageRepository packageRepository;

    @Override
    public List<Subscription> getSubscriptions(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Subscription> q = b.createQuery(Subscription.class);
        Root<Subscription> root = q.from(Subscription.class);
        q.orderBy(b.desc(root.get("createdAt")));
        q.select(root);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();
            String kw = params.get("kw");

            if (kw != null && !kw.isEmpty()) {
                Predicate codePredicate = b.like(root.get("code"), "%" + kw + "%");
                predicates.add(b.or(codePredicate));
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
    public Subscription getSubscriptionById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Subscription.class, id);

    }

    @Override
    public Subscription createOrUpdateSubscription(Subscription subscription) {
        Session s = factory.getObject().getCurrentSession();

        if (subscription.getId() == null) {
            String hql = "FROM Subscription WHERE member.id = :memberId AND status = 'ACTIVE'";
            Subscription existingSub = s.createQuery(hql, Subscription.class)
                    .setParameter("memberId", subscription.getMember().getId())
                    .uniqueResult();

            if (existingSub != null) {
                Date currentEndDate = existingSub.getEndDate();
                int daysToExtend = subscription.getGymPackage().getDuration().getDurationInDays();

                Calendar cal = Calendar.getInstance();
                cal.setTime(currentEndDate);
                cal.add(Calendar.DAY_OF_MONTH, daysToExtend);
                existingSub.setEndDate(cal.getTime());

                s.merge(existingSub);
                return existingSub;
            } else {
                s.persist(subscription);
                return subscription;
            }
        } else {
            return s.merge(subscription);
        }
    }

    @Override
    public Subscription createByPackageIdAndUsername(int packageId, String username) {
        Subscription s = new Subscription();

        s.setCode(this.subscriptionRepository.generateNextCode());
        s.setGymPackage(this.packageRepository.getPackageById(packageId));
        s.setMember(this.userRepository.getUserByUsername(username));
        s.setStatus(Subscription.SubscriptionStatus.PENDING);

        return createOrUpdateSubscription(s);
    }

    @Override
    public long countSubscriptions(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Long> q = b.createQuery(Long.class);
        Root<Subscription> root = q.from(Subscription.class);
        q.select(b.count(root));

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();
            String kw = params.get("kw");

            if (kw != null && !kw.isEmpty()) {
                Predicate codePredicate = b.like(root.get("code"), "%" + kw + "%");
                predicates.add(b.or(codePredicate));
            }

            if (!predicates.isEmpty()) {
                q.where(predicates.toArray(new Predicate[0]));
            }
        }

        return s.createQuery(q).getSingleResult();

    }

    @Override
    public String generateNextCode() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("SELECT MAX(s.id) FROM Subscription s", Integer.class);
        Integer maxId = (Integer) q.getSingleResult();

        int nextId = (maxId != null) ? maxId + 1 : 1;
        return "SUB" + String.format("%05d", nextId);
    }

    @Override
    public List<Subscription> getSubscriptionsByUsername(String username) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Subscription s WHERE s.member.username = :username", Subscription.class);
        q.setParameter("username", username);
        return q.getResultList();
    }

}
