package com.example.demo.web.services;

import java.util.UUID;

public interface BeerInventoryService {

	Integer getOnHandInventory(UUID beerId);
}
