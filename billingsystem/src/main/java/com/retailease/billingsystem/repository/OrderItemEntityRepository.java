package com.retailease.billingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retailease.billingsystem.entity.OrderItemEntity;

public interface OrderItemEntityRepository extends JpaRepository<OrderItemEntity, Long> {
}