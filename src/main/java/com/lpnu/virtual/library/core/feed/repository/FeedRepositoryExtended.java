package com.lpnu.virtual.library.core.feed.repository;

import com.lpnu.virtual.library.core.feed.model.SavedSearch;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class FeedRepositoryExtended {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Long> getOldIdsByUserLogin(String userLogin) {
        return entityManager.createQuery("select s.id from SavedSearch s where s.userLogin = ?1 order by createdDate asc",
                Long.class)
                .setParameter(1, userLogin)
                .setMaxResults(50).getResultList();
    }

    public List<Long> getAssetIdsByUserLoginDesc(String userLogin) {
        return entityManager.createQuery(
                "select s.assetId from SavedSearch s where s.userLogin = ?1 order by createdDate desc",
                Long.class)
                .setParameter(1, userLogin)
                .setMaxResults(50).getResultList();
    }
}
