package edu.jsu.mcis.cs310;

import com.github.cliftonlabs.json_simple.*;
import com.opencsv.*;
import com.opencsv.bean.*;
import java.io.FileReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Converter {
    @SuppressWarnings("unchecked")
    // Our episode class is used to marshal data betwen csv and json using the Beans objects.
    
    public static String csvToJson(String csvString) {
        String result = "{}";

        try {
            // CSV to Bean Mapping
            Map<String, String> mapping = new HashMap<>();
            mapping.put("prodNum", "ProdNum");
            mapping.put("title", "Title");
            mapping.put("season", "Season");
            mapping.put("episode", "Episode");
            mapping.put("stardate", "Stardate");
            mapping.put("originalAirdate", "OriginalAirdate");
            mapping.put("remasteredAirdate", "RemasteredAirdate");

            HeaderColumnNameTranslateMappingStrategy<Episode> strategy = new HeaderColumnNameTranslateMappingStrategy<>();
            strategy.setType(Episode.class);
            strategy.setColumnMapping(mapping);

            CSVReader csvReader = new CSVReader(new StringReader(csvString));
            CsvToBean<Episode> csvToBean = new CsvToBeanBuilder<Episode>(csvReader)
                .withMappingStrategy(strategy)
                .withIgnoreLeadingWhiteSpace(true)
                .build();

            List<Episode> list = csvToBean.parse();

            JsonObject jsonObject = new JsonObject();
            JsonArray prodNums = new JsonArray();
            JsonArray colHeadings = new JsonArray();
            JsonArray data = new JsonArray();

            // Add column headings
            colHeadings.add("ProdNum");
            colHeadings.add("Title");
            colHeadings.add("Season");
            colHeadings.add("Episode");
            colHeadings.add("Stardate");
            colHeadings.add("OriginalAirdate");
            colHeadings.add("RemasteredAirdate");

            for (Episode e : list) {
                prodNums.add(e.getProdNum());

                JsonArray row = new JsonArray();
                row.add(e.getTitle());
                row.add(e.getSeason());
                row.add(e.getEpisode());
                row.add(e.getStardate());
                row.add(e.getOriginalAirdate());
                row.add(e.getRemasteredAirdate());

                data.add(row);
            }

            jsonObject.put("ProdNums", prodNums);
            jsonObject.put("ColHeadings", colHeadings);
            jsonObject.put("Data", data);

            result = jsonObject.toJson(); // Return a string
        } 
        catch (Exception e) {
            e.printStackTrace();
        }

        return result.trim();
    }

    @SuppressWarnings("unchecked")
    public static String jsonToCsv(String jsonString) {
        String result = "";

        try {
            JsonObject jsonObject = (JsonObject) Jsoner.deserialize(jsonString);

            JsonArray colHeadings = (JsonArray) jsonObject.get("ColHeadings");
            JsonArray prodNums = (JsonArray) jsonObject.get("ProdNums");
            JsonArray data = (JsonArray) jsonObject.get("Data");

            StringWriter stringWriter = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(stringWriter);

            // Write headers
            String[] headerArray = new String[colHeadings.size()];
            for (int x = 0; x < colHeadings.size(); x++) {
                headerArray[x] = colHeadings.get(x).toString();
            }
            csvWriter.writeNext(headerArray);

            // Write rows
            for (int x = 0; x < data.size(); x++) {
                JsonArray row = (JsonArray) data.get(x);
                String[] rowArray = new String[row.size() + 1];
                rowArray[0] = prodNums.get(x).toString();
                for (int y = 0; y < row.size(); y++) {
                    String val = row.get(y).toString();
                    if (y == 2) { // if it's the Episode number we need to add a leading zero for CSV. when converted back to json it will be interpreted as int and remove it again.
                        if (Integer.parseInt(val) < 10) { // if it (converted to an integer) less than 10 add a 0 to it
                            rowArray[y + 1] = "0"+val; // We do +1 due to ProdNums being included twice in the data.
                        continue;
                        }
                    }
                    rowArray[y + 1] = val;
                }
                csvWriter.writeNext(rowArray);
            }

            csvWriter.close();
            result = stringWriter.toString();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }

        return result.trim();
    }

}
