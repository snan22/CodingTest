package com.codingtest.test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.codingtest.controller.CampaignController;
import com.codingtest.model.Campaign;
import com.codingtest.service.CampaignService;



@RunWith(SpringRunner.class)
@WebMvcTest(CampaignController.class)
public class CampaignControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  CampaignService campaignService;

  private final String URL = "/add";


  @Test
  public void testAddCampaign() throws Exception {

    // prepare data and mock's behaviour
    Campaign campaign = new Campaign(1l, "Sample Add", 80);
    when(campaignService.saveCampaign(any(Campaign.class))).thenReturn(campaign);

    // execute
    MvcResult result = mockMvc
        .perform(MockMvcRequestBuilders.post(URL).contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8).content(TestUtils.objectToJson(campaign)))
        .andReturn();

    // verify
    int status = result.getResponse().getStatus();
    assertEquals("Incorrect Response Status", HttpStatus.CREATED.value(), status);

    // verify that service method was called once
    verify(campaignService).saveCampaign(any(Campaign.class));

    Campaign resultCampaign =
        TestUtils.jsonToObject(result.getResponse().getContentAsString(), Campaign.class);
    assertNotNull(resultCampaign);
    assertEquals(new Long(1), resultCampaign.getPartnerId());

  }

  @Test
  public void testGetCampaign() throws Exception {

    // prepare data and mock's behaviour
    Campaign campaign = new Campaign(1l, "Sample Add", 80);
    when(campaignService.getCampaignInfo(any(Long.class))).thenReturn(campaign);

    // execute
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", new Long(1))
        .accept(MediaType.APPLICATION_JSON_UTF8)).andReturn();

    // verify
    int status = result.getResponse().getStatus();
    assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);

    // verify that service method was called once
    verify(campaignService).getCampaignInfo(any(Long.class));

    Campaign resultCampaign =
        TestUtils.jsonToObject(result.getResponse().getContentAsString(), Campaign.class);
    assertNotNull(resultCampaign);
    assertEquals(new Long(1), resultCampaign.getPartnerId());
  }


  @Test
  public void testUpdateCampaign() throws Exception {
    // prepare data and mock's behaviour
    // here the stub is the updated employee object with ID equal to ID of
    // employee need to be updated
    Campaign campaign = new Campaign(1l, "Sample Add", 80);
    when(campaignService.getCampaignInfo(any(Long.class))).thenReturn(campaign);

    // execute
    MvcResult result = mockMvc
        .perform(MockMvcRequestBuilders.put(URL).contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8).content(TestUtils.objectToJson(campaign)))
        .andReturn();

    // verify
    int status = result.getResponse().getStatus();
    assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);

    // verify that service method was called once
    verify(campaignService).saveCampaign(any(Campaign.class));

  }

  @Test
  public void testDeleteCampaign() throws Exception {
    // prepare data and mock's behaviour
    Campaign userStub = new Campaign(1l);
    when(campaignService.getCampaignInfo(any(Long.class))).thenReturn(userStub);

    // execute
    MvcResult result =
        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/{id}", new Long(1))).andReturn();

    // verify
    int status = result.getResponse().getStatus();
    assertEquals("Incorrect Response Status", HttpStatus.GONE.value(), status);

    // verify that service method was called once
    verify(campaignService).deleteCampaign(any(Long.class));

  }



}
