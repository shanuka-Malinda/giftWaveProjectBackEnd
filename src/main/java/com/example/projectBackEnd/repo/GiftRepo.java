package com.example.projectBackEnd.repo;

import com.example.projectBackEnd.entity.Gift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface GiftRepo extends JpaRepository<Gift,Long> {
    List<Gift> findByUserId(String userId);
}
