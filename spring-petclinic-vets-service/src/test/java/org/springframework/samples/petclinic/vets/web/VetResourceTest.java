/*
 * Copyright 2002-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.vets.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.vets.model.Vet;
import org.springframework.samples.petclinic.vets.model.VetRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static java.util.Arrays.asList;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Maciej Szarlinski
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(VetResource.class)
@ActiveProfiles("test")
class VetResourceTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    VetRepository vetRepository;

    @Test
    void shouldGetAListOfVets() throws Exception {

        Vet vet = new Vet();
        vet.setId(1);

        given(vetRepository.findAll()).willReturn(asList(vet));

        mvc.perform(get("/vets").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void shouldGetVetsById() throws Exception {

        Vet vet = new Vet();
        vet.setId(1);

        given(vetRepository.findById(1)).willReturn(Optional.of(vet));

        mvc.perform(get("/vets/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldAddVet() throws Exception {

        Vet vet = new Vet();
        vet.setId(1);

        given(vetRepository.save(vet)).willReturn(vet);

        mvc.perform(post("/vets")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\": 1}"))
            .andExpect(status().isOk());
    }

    @Test
    void shouldNotAddVet() throws Exception {

        Vet vet = new Vet();
        vet.setId(1);

        given(vetRepository.save(vet)).willReturn(vet);

        mvc.perform(post("/vets")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\": 1, \"firstName\": \"John\"}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotAddVetWithInvalidId() throws Exception {

        Vet vet = new Vet();
        vet.setId(1);

        given(vetRepository.save(vet)).willReturn(vet);

        mvc.perform(post("/vets")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\": 1, \"firstName\": \"John\", \"lastName\": \"Doe\"}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotAddVetWithInvalidFirstName() throws Exception {

        Vet vet = new Vet();
        vet.setId(1);

        given(vetRepository.save(vet)).willReturn(vet);

        mvc.perform(post("/vets")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\": 1, \"firstName\": \"\", \"lastName\": \"Doe\"}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotAddVetWithInvalidLastName() throws Exception {

        Vet vet = new Vet();
        vet.setId(1);

        given(vetRepository.save(vet)).willReturn(vet);

        mvc.perform(post("/vets")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\": 1, \"firstName\": \"John\", \"lastName\": \"\"}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotAddVetWithInvalidSpecialties() throws Exception {

        Vet vet = new Vet();
        vet.setId(1);

        given(vetRepository.save(vet)).willReturn(vet);

        mvc.perform(post("/vets")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\": 1, \"firstName\": \"John\", \"lastName\": \"Doe\"}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void shouldDeleteVet() throws Exception {

        Vet vet = new Vet();
        vet.setId(1);

        given(vetRepository.findById(1)).willReturn(Optional.of(vet));

        mvc.perform(get("/vets/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1));
    }

}
