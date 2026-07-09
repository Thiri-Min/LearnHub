package com.training.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class FillBlankPracticeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void fillBlankPracticeRedirectsToFlashcard() throws Exception {
        mockMvc.perform(get("/fill-blank-practice"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/flashcard"));
    }

    @Test
    void flashcardPageLoadsForAuthenticatedUser() throws Exception {
        User user = new User("Alex", "Nguyen", "alex", "alex@example.com", "password");
        user.setId(1L);

        mockMvc.perform(get("/flashcard").sessionAttr("loggedInUser", user))
                .andExpect(status().isOk())
                .andExpect(view().name("mk-test"))
                .andExpect(content().string(containsString("Java")))
                .andExpect(content().string(containsString("DSA")))
                .andExpect(content().string(containsString("Git")));
    }
}
