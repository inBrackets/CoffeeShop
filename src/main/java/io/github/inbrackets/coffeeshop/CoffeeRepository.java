package io.github.inbrackets.coffeeshop;

import org.springframework.data.repository.CrudRepository;

interface CoffeeRepository extends CrudRepository<Coffee, String> {}
