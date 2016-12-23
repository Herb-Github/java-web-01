package com.zz.startup.repository;

import com.zz.startup.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends BaseDao<User, Long> {

    @Modifying
    @Query(value = "insert into t_auth_user_role(user_id,role_id)values(?1,?2)", nativeQuery = true)
    int insertUserRole(Long userId, Long roleId);

    @Modifying
    @Query(value = "delete from t_auth_user_role where user_id=?1 and role_id=?2", nativeQuery = true)
    int deleteUserRole(Long userId, Long roleId);

    @Modifying
    @Query(value = "delete from t_auth_user_role where user_id=?1", nativeQuery = true)
    int deleteRoles(Long userId);
}
