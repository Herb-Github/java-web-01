package com.zz.startup.repository;

import com.zz.startup.entity.Role;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleDao extends BaseDao<Role, Long> {

    @Query(value = "select a.* from t_auth_role a, t_auth_user_role b where a.id=b.role_id and b.user_id=?1", nativeQuery = true)
    List<Role> queryUserRoles(Long id);

    @Modifying
    @Query(value = "insert into t_auth_role_authority(role_id,authority_id)values(?1,?2)", nativeQuery = true)
    int insertRoleAuthority(Long roleId, Long authorityId);

    @Modifying
    @Query(value = "delete from t_auth_role_authority where role_id=?1 and authority_id=?2", nativeQuery = true)
    int deleteRoleAuthority(Long roleId, Long authorityId);

    @Modifying
    @Query(value = "delete from t_auth_role_authority where role_id=?1", nativeQuery = true)
    int deleteAuthorities(Long roleId);
}
