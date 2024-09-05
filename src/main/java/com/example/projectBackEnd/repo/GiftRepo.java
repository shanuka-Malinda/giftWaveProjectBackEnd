package com.example.projectBackEnd.repo;

import com.example.projectBackEnd.entity.Gift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GiftRepo extends JpaRepository<Gift,Long> {
}
