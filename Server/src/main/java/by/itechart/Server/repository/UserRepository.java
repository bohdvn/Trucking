package by.itechart.Server.repository;

import by.itechart.Server.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Page<User> findAll(Pageable pageable);

    Optional<User> findByLoginOrEmail(String login, String email);

    boolean existsByLogin(String login);

    boolean existsByEmail(String email);
}
