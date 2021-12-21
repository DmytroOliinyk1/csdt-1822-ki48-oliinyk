package com.lpnu.virtual.library.core.feed.repository;

import com.lpnu.virtual.library.core.feed.model.SavedSearch;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedRepository extends JpaRepository<SavedSearch, Long> {
    @Query("select distinct s.userLogin from SavedSearch s")
    public List<String> getUniqueUser();

    @Query("select count(s) from SavedSearch s where s.userLogin = ?1")
    public Long getCountByUserLogin(String userLogin);
}
