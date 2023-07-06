package com.example.demo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ParentController {
// private static final int PAGE_SIZE = 2;

    private List<ParentTransaction> parentData;
    private List<ChildInstallment.Data> childData;
    private int PAGE_SIZE = 0;
    public ParentController() {
        parentData = loadDataFromFile();
        childData = loadDataFromChildFile();
    }

    private ArrayList<ChildInstallment.Data> loadDataFromChildFile() {
        try {
        ClassPathResource resource1 = new ClassPathResource("Child.json");
        ObjectMapper mapper1 = new ObjectMapper();
        Gson gson = new Gson();
        Type type =new TypeToken<ChildInstallment>(){}.getType();
        String result = null;

            result = new BufferedReader(new InputStreamReader(resource1.getInputStream()))
                    .lines().collect(Collectors.joining("\n"));


        ChildInstallment arrayList = gson.fromJson(result,type);
        return arrayList.getData();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<ParentTransaction> loadDataFromFile() {
        try {
            ClassPathResource resource = new ClassPathResource("Parent.json");
            ObjectMapper mapper = new ObjectMapper();
            ParentDataWrapper dataWrapper = mapper.readValue(resource.getInputStream(), ParentDataWrapper.class);
            return dataWrapper.getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/parents")
    public ResponseEntity<ParentNewModel> getParentTransactions(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "2") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy
    ) {

        int sum =0;

        int startIndex = (pageNumber - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, parentData.size());
        List<ParentTransaction> paginatedParents =  parentData.subList(startIndex, endIndex);

        // Sort by parent ID
        if (sortBy.equalsIgnoreCase("id")) {
            paginatedParents = new ArrayList<>(paginatedParents);
        }

        for (int i = 0; i<paginatedParents.size();i++) {
            for (int j = 0; j < childData.size(); j++) {
                if (paginatedParents.get(i).getId() == childData.get(j).getParentId()){
                    paginatedParents.get(i).setTotalPaidAmount(paginatedParents.get(i).getTotalPaidAmount() + childData.get(j).getPaidAmount());
                }
            }
        }


        ParentNewModel model = new ParentNewModel(parentData.size(),paginatedParents);

        return new ResponseEntity<>(model, HttpStatus.OK);
    }

}
