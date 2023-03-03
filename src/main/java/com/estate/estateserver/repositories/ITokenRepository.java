package com.estate.estateserver.repositories;

import com.estate.estateserver.models.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ITokenRepository extends JpaRepository<TokenEntity, Integer> {

    @Query(value = "select t from TOKENS t inner join User u on t.user.id = u.id where u.id = :id and (t.expired = false or t.revoked = false)")
    List<TokenEntity> findAllValidTokenByUser(Integer id);

    Optional<TokenEntity> findByToken(String tokenEntity);

}
