package ru.ksenia.NauJava;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ksenia.NauJava.controller.SetupController;
import ru.ksenia.NauJava.entity.*;
import ru.ksenia.NauJava.enums.PedalTypes;
import ru.ksenia.NauJava.repository.SetupRepository;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SetupControllerTest {
    @Mock
    private SetupRepository setupRepository;

    @InjectMocks
    private SetupController setupController;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.standaloneSetup(setupController);
    }

    @Test
    void findByPedalType200ReturnsList() {
        Setup setup = new Setup("test", null, null, null, null, "config", null);
        setup.setId(1L);
        when(setupRepository.findAllContainingPedalType(PedalTypes.DISTORTION)).thenReturn(List.of(setup));
        RestAssuredMockMvc.given().contentType(ContentType.JSON).queryParam("type", "DISTORTION")
                .when().get("/custom/setups/findByPedalType")
                .then().statusCode(200).contentType(ContentType.JSON).body("$", hasSize(1))
                .body("[0].id", equalTo(1)).body("[0].name", equalTo("test"));
        verify(setupRepository).findAllContainingPedalType(PedalTypes.DISTORTION);
    }

    @Test
    void findByPedalType200EmptyResult() {
        when(setupRepository.findAllContainingPedalType(PedalTypes.DISTORTION)).thenReturn(Collections.emptyList());
        RestAssuredMockMvc.given().contentType(ContentType.JSON).queryParam("type", "DISTORTION")
                .when().get("/custom/setups/findByPedalType")
                .then().statusCode(200).contentType(ContentType.JSON).body("$", hasSize(0));
        verify(setupRepository).findAllContainingPedalType(PedalTypes.DISTORTION);
    }

    @Test
    void findByPedalType400MissingParam() {
        RestAssuredMockMvc.given().contentType(ContentType.JSON).when().get("/custom/setups/findByPedalType")
                .then().statusCode(400);
        verifyNoInteractions(setupRepository);
    }

    @Test
    void findByPedalType400InvalidEnum() {
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .queryParam("type", "INVALID_TYPE")
                .when().get("/custom/setups/findByPedalType")
                .then().statusCode(400);
        verifyNoInteractions(setupRepository);
    }

    @Test
    void existsByUserAndName200Exists() {
        long userId = 1L;
        String setupName = "test";
        when(setupRepository.existsByUserIdAndName(userId, setupName)).thenReturn(true);
        RestAssuredMockMvc.given().contentType(ContentType.JSON).queryParam("userId", userId).queryParam("name", setupName)
                .when().get("/custom/setups/existsByUserAndName")
                .then().statusCode(200).body(equalTo("true"));
        verify(setupRepository).existsByUserIdAndName(userId, setupName);
    }

    @Test
    void existsByUserAndName200NotExists() {
        long userId = 1L;
        String setupName = "test";
        when(setupRepository.existsByUserIdAndName(userId, setupName)).thenReturn(false);
        RestAssuredMockMvc.given().contentType(ContentType.JSON).queryParam("userId", userId).queryParam("name", setupName)
                .when().get("/custom/setups/existsByUserAndName")
                .then().statusCode(200).body(equalTo("false"));
        verify(setupRepository).existsByUserIdAndName(userId, setupName);
    }

    @Test
    void existsByUserAndName400MissingParam() {
        long userId = 1L;
        RestAssuredMockMvc.given().contentType(ContentType.JSON).queryParam("userId", userId)
                .when().get("/custom/setups/existsByUserAndName")
                .then().statusCode(400);
        verifyNoInteractions(setupRepository);
    }

    @Test
    void existsByUserAndName400InvalidUserIdType() {
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .queryParam("userId", "invalid_id")
                .queryParam("name", "test")
                .when().get("/custom/setups/existsByUserAndName")
                .then().statusCode(400);
        verifyNoInteractions(setupRepository);
    }

}
