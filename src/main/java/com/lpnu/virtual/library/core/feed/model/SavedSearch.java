package com.lpnu.virtual.library.core.feed.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "saved_search")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class SavedSearch {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_login")
    private String userLogin;

    @Column(name = "asset_id")
    private Long assetId;

    @Column(name = "created_date", nullable = false, updatable = false)
    private Long createdDate;
}
