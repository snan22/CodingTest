package com.codingtest.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingtest.model.Campaign;
import com.codingtest.repository.CampaignRepository;
import com.codingtest.service.CampaignService;


@Service
public class CampaignServiceImpl implements CampaignService {

  private static Logger log = Logger.getLogger(CampaignServiceImpl.class);
  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  CampaignRepository campaignRepo;

  public CampaignRepository getCampaignRepo() {
    return campaignRepo;
  }

  @Autowired
  public void setCampaignRepo(CampaignRepository campaignRepo) {
    this.campaignRepo = campaignRepo;
  }

  public Campaign saveCampaign(Campaign campaign) {
    log.debug("before saving campaign into DB");
    Campaign savedCampaign = null;
    try {
      Calendar calendar = Calendar.getInstance(); // gets a calendar using the default time zone and
      // locale.
      calendar.add(Calendar.SECOND, campaign.getDuration());
      String formatted = dateFormat.format(calendar.getTime());
      campaign.setCampaignExpireTime(formatted);
      savedCampaign = campaignRepo.save(campaign);
    } catch (Exception e) {
      log.error("Unable save campaign in DB " + e);
    }
    log.debug("After Saving campaign into DB");
    return savedCampaign;
  }

  public boolean deleteCampaign(long id) {
    boolean flag = false;
    log.debug("before deleting campaign from DB");
    try {
      campaignRepo.delete(id);
      flag = true;
    } catch (Exception e) {
      log.error("Unable delete campaign from DB" + e.getMessage());
    }
    log.debug("After deleting campaign from DB");
    return flag;
  }

  public Campaign getCampaignInfo(long id) {
    Campaign campaign = null;
    log.debug("before getting campaign from DB");
    try {
      campaign = campaignRepo.findOne(id);
    } catch (Exception e) {
      log.error("Unable find the campaign from DB");
    }
    log.debug("After getting campaign from DB");
    return campaign;
  }

  @Override
  public List<Campaign> findAllCampaigns() {
    List<Campaign> campaigns = campaignRepo.findAll();
    return campaigns;
  }



}
