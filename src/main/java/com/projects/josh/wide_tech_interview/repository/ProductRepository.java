/*
 */

package com.projects.josh.wide_tech_interview.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.projects.josh.wide_tech_interview.model.Product;

/**
 *
 * @author USER
 */
public interface ProductRepository extends JpaRepository<Product, Long>{
    @Override
    Page<Product> findAll(Pageable pageable);

}
