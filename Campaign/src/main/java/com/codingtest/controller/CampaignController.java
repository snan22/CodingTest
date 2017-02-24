package com.codingtest.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codingtest.model.Campaign;
import com.codingtest.service.CampaignService;


@Controller
@RequestMapping("/ad")
public class CampaignController {

  private static Logger log = Logger.getLogger(CampaignController.class);
  @Autowired
  CampaignService campaignService;
  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<Campaign> saveCampaign(@RequestBody Campaign campaign) {
    if (null != campaign) {
      campaignService.saveCampaign(campaign);
      campaign.setStatus("ACTIVE");
      log.debug("Added:: " + campaign);
    }
    return new ResponseEntity(campaign, HttpStatus.CREATED);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResponseEntity<Object> getCampaign(@PathVariable("id") long id) throws ParseException {
    Campaign campaign = campaignService.getCampaignInfo(id);
    if (campaign != null) {
      String time = campaign.getCampaignExpireTime();
      Date current = new Date();
      boolean flag = dateFormat.parse(time).after(dateFormat.parse(dateFormat.format(current)));
      if (flag) {
        campaign.setStatus("ACTIVE");
        return new ResponseEntity(campaign, HttpStatus.OK);
      }
      log.debug("campaign  with id " + id + "does not exists");
      return new ResponseEntity("This not an Active campaign", HttpStatus.NOT_FOUND);
    }
    log.debug("Found Campaign:: " + campaign);
    return new ResponseEntity("No campaign found for " + id, HttpStatus.NOT_FOUND);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<String> deleteCampaign(@PathVariable("id") int id) {
    Campaign campaign = campaignService.getCampaignInfo(id);
    if (null != campaign) {
      campaignService.deleteCampaign(id);
      log.debug("deleted Campaign:: with id" + id);
      return new ResponseEntity<String>("Campaign Deleted Successfully", HttpStatus.GONE);
    } else {
      log.debug("campaign  with id" + id + " does not exists");
      return new ResponseEntity<String>("Campaign Not Found", HttpStatus.NOT_FOUND);
    }
  }

  @RequestMapping(method = RequestMethod.PUT)
  public ResponseEntity<Object> updateCampaignInfo(@RequestBody Campaign campaign) throws ParseException {
    Campaign existingCampaign = campaignService.getCampaignInfo(campaign.getPartnerId());
    if (existingCampaign == null) {
      log.debug("Campaign with id " + campaign.getPartnerId() + "does not exists");
      return new ResponseEntity("Campaign Not Found", HttpStatus.NOT_FOUND);
    } else {
      
      Campaign updatedCampaign = campaignService.saveCampaign(campaign);
      
      String time = campaign.getCampaignExpireTime();
      Date current = new Date();
      boolean flag = dateFormat.parse(time).after(dateFormat.parse(dateFormat.format(current)));
      if (flag) {
        updatedCampaign.setStatus("ACTIVE");
        log.debug("Campaign updated" + updatedCampaign);
        return new ResponseEntity(updatedCampaign, HttpStatus.OK);
      }
      return new ResponseEntity("Campaign Not Found", HttpStatus.NOT_FOUND);
    }
  }
  
  
  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<Object> getAllCampaign() throws ParseException {
    List<Campaign> activeCampaigns=new ArrayList();
    List<Campaign> campaigns = campaignService.findAllCampaigns();
    for(Campaign campaign:campaigns){
    if (campaign != null) {
      String time = campaign.getCampaignExpireTime();
      Date current = new Date();
      boolean flag = dateFormat.parse(time).after(dateFormat.parse(dateFormat.format(current)));
      if (flag) {
        campaign.setStatus("ACTIVE");
        activeCampaigns.add(campaign);
      }
    }
    }
    if(!activeCampaigns.isEmpty()){
      return new ResponseEntity(activeCampaigns, HttpStatus.OK);
    }else{
      return new ResponseEntity("No Campaign found for ", HttpStatus.NOT_FOUND);
    }
  }


}
