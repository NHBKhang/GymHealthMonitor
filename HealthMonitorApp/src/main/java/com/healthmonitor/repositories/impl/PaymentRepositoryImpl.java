package com.healthmonitor.repositories.impl;

import com.healthmonitor.pojo.Payment;
import com.healthmonitor.pojo.Payment.PaymentStatus;
import static com.healthmonitor.pojo.Payment.PaymentStatus.CANCELLED;
import static com.healthmonitor.pojo.Payment.PaymentStatus.FAILED;
import static com.healthmonitor.pojo.Payment.PaymentStatus.PENDING;
import static com.healthmonitor.pojo.Payment.PaymentStatus.SUCCESS;
import com.healthmonitor.pojo.Subscription;
import com.healthmonitor.pojo.Subscription.SubscriptionStatus;
import com.healthmonitor.repositories.PaymentRepository;
import com.healthmonitor.services.SubscriptionService;
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
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class PaymentRepositoryImpl implements PaymentRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private SubscriptionService subscriptionService;

    @Override
    public List<Payment> getPayments(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Payment> q = b.createQuery(Payment.class);
        Root<Payment> root = q.from(Payment.class);
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
    public Payment getPaymentById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Payment.class, id);

    }

    @Override
    public Payment createOrUpdatePayment(Payment payment) {
        Session s = factory.getObject().getCurrentSession();

        if (payment.getId() == null) {
            s.persist(payment);
        } else {
            Payment existing = this.getPaymentById(payment.getId());
            if (existing != null) {
                Subscription sub = existing.getSubscription();

                PaymentStatus status = payment.getStatus();
                SubscriptionStatus subStatus = sub.getStatus();
                switch (status) {
                    case PENDING ->
                        subStatus = SubscriptionStatus.PENDING;
                    case SUCCESS -> {
                        subStatus = SubscriptionStatus.ACTIVE;

                        if (sub.getStartDate() == null) {
                            sub.setStartDate(new Date());
                        }
                        if (sub.getEndDate() == null) {
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(sub.getStartDate());
                            cal.add(Calendar.DATE, sub.getGymPackage().getDuration().getDurationInDays());
                            sub.setEndDate(cal.getTime());
                        }
                    }
                    case FAILED ->
                        subStatus = SubscriptionStatus.INACTIVE;
                    case CANCELLED ->
                        subStatus = SubscriptionStatus.CANCELLED;
                    default -> {
                    }
                }

                sub.setStatus(subStatus);
                subscriptionService.createOrUpdateSubscription(sub);

                existing.setStatus(status);
                existing.setDescription(payment.getDescription());

                payment = s.merge(existing);
            } else {
                payment = s.merge(payment);
            }
        }

        return payment;
    }

    @Override
    public long countPayments(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Long> q = b.createQuery(Long.class
        );
        Root<Payment> root = q.from(Payment.class
        );
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
}
