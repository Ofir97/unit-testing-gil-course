package testingil.unittesting.examples.exercise.e03.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@ContextConfiguration(classes = {CalculatorControllerConfiguration.class})
public class CalculatorDisplaySpringControllerTests {

    MockMvc mockMvc;

    @Autowired
    CalculatorController controller;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @AfterEach
    public void resetCalculator() {
        controller.press("C");
    }

    @Test
    public void whenPressDigit_thenDisplayDigit() throws Exception {
        pressSequence("1");
        Assertions.assertEquals("1", getDisplay());
    }

    @Test
    void whenPlusTwoDigits_thenDisplaySum() throws Exception {
        pressSequence("2+5=");
        assertEquals("7.0", getDisplay());
    }

    @Test
    void whenMinusTwoDigits_thenDisplaySubtraction() throws Exception {
        pressSequence("7-4=");
        assertEquals("3.0", getDisplay());
    }

    @Test
    void whenMulTwoDigits_thenDisplayMultiplication() throws Exception {
        pressSequence("6*4=");
        assertEquals("24.0", getDisplay());
    }

    @Test
    void whenDivideTwoDigits_thenDisplayDivision() throws Exception {
        pressSequence("5/2=");
        assertEquals("2.5", getDisplay());
    }

    @Test
    void whenPressC_thenDisplayZero() throws Exception {
        pressSequence("C=");
        assertEquals("0", getDisplay());
    }

    @Test
    void whenSumTwoNegatives_thenDisplayNegativeSum() throws Exception {
        pressNegative("-4");
        pressSequence("+");
        pressNegative("-2");
        pressSequence("=");
        assertEquals("-6.0", getDisplay());
    }

    @Test
    void whenPressNumberStartWithZero_thenIgnoreZero() throws Exception {
        pressSequence("062=");
        assertEquals("62", getDisplay());
    }

    @Test
    void whenPressMultipleZeros_thenDisplayZero() throws Exception {
        pressSequence("000000000=");
        assertEquals("0", getDisplay());
    }

    private void pressSequence(String sequence) throws Exception {
        sequence.chars().mapToObj(i -> (char) i).forEach(c -> {
            try {
                press(c);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
    }

    private void pressNegative(String negativeNumber) throws Exception {
        pressNegativeMvc(negativeNumber);
    }

    private void press(char key) throws Exception {
        mockMvc.perform(post("/calculator/press")
                .param("key", Character.toString(key)));
    }

    private void pressNegativeMvc(String negativeNumber) throws Exception {
        mockMvc.perform(post("/calculator/press")
                .param("key", negativeNumber));
    }

    private String getDisplay() throws Exception {
        MvcResult response = mockMvc.perform(get("/calculator/display")).andExpect(status().isOk()).andReturn();
        return response.getResponse().getContentAsString();
    }
}
