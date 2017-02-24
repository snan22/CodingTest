package com.codingtest.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.codingtest.model.Campaign;

@Service
public interface CampaignService {
  public Campaign saveCampaign(Campaign user);

  public boolean deleteCampaign(long id);

  public Campaign getCampaignInfo(long id);

  public List<Campaign> findAllCampaigns();
}
