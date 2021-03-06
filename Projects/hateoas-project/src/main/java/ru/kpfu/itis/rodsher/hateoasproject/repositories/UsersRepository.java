package ru.kpfu.itis.rodsher.hateoasproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.rodsher.hateoasproject.models.User;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
}
