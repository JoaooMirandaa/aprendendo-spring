package com.joaovitor.aprendendospring.infrastructure.repository;

import com.joaovitor.aprendendospring.infrastructure.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EndercoRepository extends JpaRepository<Endereco,Long> {
}
