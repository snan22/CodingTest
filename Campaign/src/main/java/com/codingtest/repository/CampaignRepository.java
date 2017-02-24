package com.codingtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codingtest.model.Campaign;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {

}
