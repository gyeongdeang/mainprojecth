package com.example.mainprojecth.members;


import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
   // @EntityGraph(attributePaths = "authorities")
    //Optional<Member> findOneWithAuthoritiesByEmail(String email);
}