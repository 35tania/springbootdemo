package com.tania.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tania.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {



}
