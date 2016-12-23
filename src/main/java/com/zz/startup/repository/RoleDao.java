package com.zz.startup.repository;

import com.zz.startup.entity.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleDao extends BaseDao<Role, Long> {

    @Query(value = "select a.* from t_auth_role a, t_auth_user_role b where a.id=b.role_id and b.user_id=?1", nativeQuery = true)
    List<Role> queryUserRoles(Long id);
}
