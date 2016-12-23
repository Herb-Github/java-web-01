package com.zz.startup.repository;

import com.zz.startup.entity.Authority;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface AuthorityDao extends BaseDao<Authority, Long> {

    @Query(value = "select a.* from t_auth_authority a, t_auth_role_authority ra where a.id=ra.authority_id " +
            "and ra.role_id in (select r.id from t_auth_role r, t_auth_user_role ur where r.id=ur.role_id and ur.user_id=?1)", nativeQuery = true)
    Set<Authority> queryUserAuthorities(Long userId);

    @Query(value = "select a.* from t_auth_authority a, t_auth_role_authority ra " +
            "where a.id=ra.authority_id and ra.role_id=?1", nativeQuery = true)
    List<Authority> queryRoleAuthorities(Long roleId);
}
