package com.example.Sale_Campaign.Service;

import com.example.Sale_Campaign.Model.Product;
import com.example.Sale_Campaign.Repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    ProductRepository productRepository;

    @PostConstruct
    public void generateReport() throws IOException {
        String filePath = "inventory1.csv";
        List<Product> products = productRepository.findAll();
        System.out.println("Total products fetched: " + products.size());

        if (products.isEmpty()) {
            System.out.println("No products found. Report not generated.");
            return;
        }

        try (FileWriter fileWriter = new FileWriter(filePath);
             ICsvBeanWriter csvWriter = new CsvBeanWriter(fileWriter, CsvPreference.STANDARD_PREFERENCE)) {

            // Define headerkus
            String[] headers = {"id", "title", "mrp", "currentPrice", "discount", "inventory"};


            csvWriter.writeHeader(headers);

            // Write products to CSV
            for (Product product : products) {
                System.out.println("Writing product: " + product);
                csvWriter.write(product, headers);
            }

            System.out.println("✅ Report generated successfully at: " + filePath);

        } catch (IOException e) {
            System.err.println("❌ Error generating report: " + e.getMessage());
        }
    }
}



