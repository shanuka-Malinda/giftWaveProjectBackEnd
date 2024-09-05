package com.example.projectBackEnd.repo;

import com.example.projectBackEnd.entity.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemsRepo extends JpaRepository<Items,Long> {
}
