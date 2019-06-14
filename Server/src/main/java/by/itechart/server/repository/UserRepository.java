package by.itechart.server.repository;

import by.itechart.server.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>, JpaSpecificationExecutor<User> {

    Page<User> findAllByClientCompanyId(Integer id,Pageable pageable);

    Optional<User> findByLoginOrEmail(String login, String email);

    boolean existsByLogin(String login);

    boolean existsByEmail(String email);

    User findByEmailIgnoreCase(String email);

    Page<User> findAllByRolesContains(User.Role role,Pageable pageable);

    List<User> findAllByRolesContains(User.Role role);
}
