package com.ogirafferes.lxp.identity.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Column(nullable = false, unique = true, length = 50)
    private String name;  // USER / ADMIN

    @Column(columnDefinition = "TEXT")
    private String description;

    public Role(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // 도메인 로직
    public boolean isAdmin() {
        return "ADMIN".equals(name);
    }
}
