package com.projectRestAPI.MyShop.repository;

import com.projectRestAPI.MyShop.dto.StatisticsInterface.GenderCount;
import com.projectRestAPI.MyShop.dto.StatisticsInterface.RoleCount;
import com.projectRestAPI.MyShop.dto.StatisticsInterface.UserMonthlyCount;
import com.projectRestAPI.MyShop.dto.StatisticsInterface.UserStatisticsByMonth;
import com.projectRestAPI.MyShop.model.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends BaseRepository<Users,Long>{
    Boolean existsByUsername(String userName);
    Optional<Users> findByUsername(String username);

    boolean existsByEmail(String email);
    Optional<Users> findByEmail(String email);

//    @Query(value = """
//        SELECT
//            DATE_FORMAT(created_date, '%Y-%m') AS month,
//            COUNT(*) AS icount,
//            COUNT(CASE WHEN gender = 1 THEN 1 END) AS maleCount,
//            COUNT(CASE WHEN gender = 0 THEN 1 END) AS femaleCount,
//            COUNT(CASE WHEN gender IS NULL THEN 1 END) AS otherCount
//        FROM users
//        GROUP BY DATE_FORMAT(created_date, '%Y-%m')
//        ORDER BY month
//        """, nativeQuery = true)
//    List<UserStatisticsByMonth> getUserStatisticsByMonth();

    @Query(
            value = "SELECT DATE_FORMAT(u.created_date, '%Y-%m') AS month, COUNT(*) AS total_users " +
                    "FROM users u " +
                    "JOIN users_roles ur ON u.id = ur.users_id " +
                    "JOIN roles r ON ur.roles_id = r.id " +
                    "WHERE r.name = 'USER' " +
                    "GROUP BY month " +
                    "ORDER BY month",
            nativeQuery = true
    )
    List<UserMonthlyCount> getUserCountByMonth();

    @Query(
            value = "SELECT r.name AS name, COUNT(*) AS total " +
                    "FROM users u " +
                    "JOIN users_roles ur ON u.id = ur.users_id " +
                    "JOIN roles r ON ur.roles_id = r.id " +
                    "GROUP BY r.name",
            nativeQuery = true
    )
    List<RoleCount> getRoleCounts();

    @Query(
            value = "SELECT " +
                    "COUNT(*) AS total, " +
                    "COUNT(CASE WHEN u.gender = 1 THEN 1 END) AS male, " +
                    "COUNT(CASE WHEN u.gender = 0 THEN 1 END) AS female, " +
                    "COUNT(CASE WHEN u.gender IS NULL THEN 1 END) AS other " +
                    "FROM users u " +
                    "JOIN users_roles ur ON u.id = ur.users_id " +
                    "JOIN roles r ON ur.roles_id = r.id " +
                    "WHERE r.name = 'USER'",
            nativeQuery = true
    )
    GenderCount getGenderCounts();


}
