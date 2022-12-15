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

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.samples.petclinic.vets.model.Vet;
import org.springframework.samples.petclinic.vets.model.VetRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
class VetResource {

    private final VetRepository vetRepository;

    @GetMapping("/vets")
    public List<Vet> showResourcesVetList() {
        return vetRepository.findAll();
    }

    @GetMapping("/{vetId}")
    public Optional<Vet> findVet(@PathVariable("vetId") int vetId) {
        return vetRepository.findById(vetId);
    }

    @PostMapping("/vets")
    public void addVet(Vet vet) {
        vetRepository.save(vet);
    }

    @DeleteMapping("/{vetId}")
    public void deleteVet(@PathVariable("vetId") int vetId) {
        vetRepository.deleteById(vetId);
    }

}
