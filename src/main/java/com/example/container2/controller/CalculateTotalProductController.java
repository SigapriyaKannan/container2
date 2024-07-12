package com.example.container2.controller;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/calculateTotalProduct")
public class CalculateTotalProductController {

    @PostMapping
    public ResponseEntity<?> calculateTotalProduct(@RequestBody Map<String, String> request) {
        String file = request.get("file");
        String product = request.get("product");
        String fileName = new File(file).getName();

        int total = 0;
        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            String[] headers = reader.readNext();
            if (headers == null) {
                return new ResponseEntity<>(Map.of("file", fileName, "error", "Input file not in CSV format."), HttpStatus.BAD_REQUEST);
            }

            int productIndex = -1;
            int amountIndex = -1;
            for (int i = 0; i < headers.length; i++) {
                if (headers[i].trim().equals("product")) {
                    productIndex = i;
                } else if (headers[i].trim().equals("amount")) {
                    amountIndex = i;
                }
            }

            if (productIndex == -1 || amountIndex == -1) {
                return new ResponseEntity<>(Map.of("file", fileName, "error", "Input file not in CSV format."), HttpStatus.BAD_REQUEST);
            }

            String[] line;
            while ((line = reader.readNext()) != null) {
                if (line[productIndex].trim().equals(product)) {
                    total += Integer.parseInt(line[amountIndex].trim());
                }
            }

        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("file", fileName, "error", "Input file not in CSV format."), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(Map.of("file", fileName, "sum", total), HttpStatus.OK);
    }
}
