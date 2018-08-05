package com.oakinvest.cerise.myserver.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Current exchange rate implementation test.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MyCurrentExchangeRateTest {

    @Autowired
    private MockMvc mvc;

    /**
     * Test for Current exchange rate results.
     */
    @Test
    public void getCurrentExchangeRateResults() throws Exception {
        // Testing all the data.
        mvc.perform(get("/")
                .param("mode", "rate")
                .param("cp", "XBTUSD-ver4,2")
                .param("type", "typical,high")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(not(containsString("\\n\\r"))))
                .andExpect(jsonPath("$", hasSize(1)))
                // First result.
                .andExpect(jsonPath("$[0].cp").value("MY_TEST"))
                .andExpect(jsonPath("$[0].time").value(1488767410.5463133))
                .andExpect(jsonPath("$[0].rates.typical").value(1349.332215))
                .andExpect(jsonPath("$[0].rates.high").value(1351.2))
                .andExpect(jsonPath("$[1].nonce").doesNotExist())
                .andExpect(jsonPath("$[0].signature").doesNotExist());
    }

}
