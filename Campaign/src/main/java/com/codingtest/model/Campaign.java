package com.codingtest.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "campaign")
public class Campaign implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  @Id
  // @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "partener_id")
  private Long partnerId;
  @Column(name = "expire_time")
  @JsonIgnore
  private String campaignExpireTime;
  private String adContent;
  private int duration;
  @Transient
  private String status;

  public Long getPartnerId() {
    return partnerId;
  }

  public void setPartnerId(Long partnerId) {
    this.partnerId = partnerId;
  }

  public String getCampaignExpireTime() {
    return campaignExpireTime;
  }

  public void setCampaignExpireTime(String campaignExpireTime) {
    this.campaignExpireTime = campaignExpireTime;
  }

  public String getAdContent() {
    return adContent;
  }

  public void setAdContent(String adContent) {
    this.adContent = adContent;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Campaign(Long partnerId, String adContent, int duration) {
    super();
    this.partnerId = partnerId;
    this.adContent = adContent;
    this.duration = duration;
  }

  public Campaign(Long partnerId) {
    super();
    this.partnerId = partnerId;
  }

  public Campaign() {
    super();
  }
}
