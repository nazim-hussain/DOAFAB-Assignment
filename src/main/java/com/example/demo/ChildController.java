package com.example.demo;

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
import java.util.List;
import java.util.stream.Collectors;


@RestController
class ChildController {
    private ArrayList<ChildInstallment.Data> childData;
    private List<ParentTransaction> parentData;

    public ChildController() {
        parentData = loadDataFromParentFile();
        childData = loadDataFromFile();
    }

    private List<ParentTransaction> loadDataFromParentFile() {
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


    private ArrayList<ChildInstallment.Data> loadDataFromFile() {
        try {
            ClassPathResource resource = new ClassPathResource("Child.json");
            ObjectMapper mapper = new ObjectMapper();
            Gson gson = new Gson();
            Type type =new TypeToken<ChildInstallment>(){}.getType();
            String result = new BufferedReader(new InputStreamReader(resource.getInputStream()))
                    .lines().collect(Collectors.joining("\n"));

           ChildInstallment arrayList = gson.fromJson(result,type);

//            ChildDataWrapper dataWrapper = mapper.readValue(resource.getInputStream(), ChildDataWrapper.class);
            return arrayList.getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/children")
    public ResponseEntity<ArrayList<NewChildModel>> getChildData(
            @RequestParam(defaultValue = "0") int id
    ) {
        ArrayList<ChildInstallment.Data> sortedChildData = childData;
//                .sorted(Comparator.comparingInt(ChildInstallment::getId))
//                .collect(Collectors.toList());


        ArrayList<ChildInstallment.Data> onlyParentList = new ArrayList<>();
        for (int k=0 ;k<sortedChildData.size();k++){
            if (sortedChildData.get(k).getParentId() == id){
                onlyParentList.add(sortedChildData.get(k));
            }
        }

        ArrayList<NewChildModel> arrayList = new ArrayList<>();

        for (int i = 0; i<onlyParentList.size();i++){
            NewChildModel model = new NewChildModel();
            model.setId(onlyParentList.get(i).getId());
            model.setPaidAmount(onlyParentList.get(i).getPaidAmount());
            for (int j=0; j<parentData.size();j++){
                if (onlyParentList.get(i).getParentId() == parentData.get(j).getId()){
                    model.setReceiver(parentData.get(j).getReceiver());
                    model.setSender(parentData.get(j).getSender());
                    model.setTotalAmount(parentData.get(j).getTotalAmount());
                }
            }

            arrayList.add(model);
        }



        return new ResponseEntity<>(arrayList, HttpStatus.OK);
    }
}
