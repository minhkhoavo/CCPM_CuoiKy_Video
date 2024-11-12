package com.restaurant.management.service;

import com.restaurant.management.model.DiningTable;
import com.restaurant.management.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TableService {

    private final TableRepository tableRepository;

    @Autowired
    public TableService(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    public List<DiningTable> getAllTables() {
        return tableRepository.findAll();
    }

    public Optional<DiningTable> getTableById(Long id) {
        return tableRepository.findById(id);
    }

    public DiningTable saveTable(DiningTable diningTable) {
        return tableRepository.save(diningTable);
    }

    public void updateTable(Long id, DiningTable diningTable) {
        DiningTable existingTable = tableRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Invalid table Id:" + id));

        existingTable.setTableNumber(diningTable.getTableNumber());
        existingTable.setStatus(diningTable.getStatus());

        tableRepository.save(existingTable);
    }

    public void deleteTable(Long id) {
        tableRepository.deleteById(id);
    }
}
