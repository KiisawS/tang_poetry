package com.tang_poetry.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tang_poetry.domain.Poetries;
import com.tang_poetry.domain.Poets;
import com.tang_poetry.repository.PoetriesRepository;
import com.tang_poetry.repository.PoetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class ActionController {

    @Autowired
    private PoetriesRepository poetriesRepository;
    @Autowired
    private PoetsRepository poetsRepository;

    @GetMapping("/findByTitle/{title}")
    public List<JSONObject> findByTitle(@PathVariable String title) {
        List<JSONObject> jsonObjects = Lists.newArrayList();
        List<Poetries> poetriesList = poetriesRepository.findByTitleIsLike(title+"%");
        Set<Integer> poetsIdSet = poetriesList.stream().map(Poetries::getPoetId).collect(Collectors.toSet());
        Map<Integer, String> idAndName = Maps.newHashMap();
        if (!poetsIdSet.isEmpty()) {
            List<Poets> poetsList = poetsRepository.findAllById(poetsIdSet);
            idAndName = poetsList.stream()
                    .collect(Collectors.toMap(Poets::getId, Poets::getName));
        }
        idAndName.entrySet().forEach(
             entity -> {
                 List<Poetries> subList = poetriesList.stream()
                         .filter(poetries -> poetries.getPoetId().equals(entity.getKey()))
                         .collect(Collectors.toList());
                 JSONObject jsonObject = new JSONObject();
                 jsonObject.put("name", entity.getValue());
                 jsonObject.put("poetries", subList);
                 jsonObjects.add(jsonObject);
             }
        );

        return jsonObjects;
    }

    @GetMapping("/findByName/{name}")
    public JSONObject findByName(@PathVariable String name) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        Poets poets = poetsRepository.findByName(name);
        if (poets != null) {
            List<Poetries> poetriesList = poetriesRepository.findAllByPoetId(poets.getId());
            jsonObject.put("poetries", poetriesList);
        } else {
            jsonObject.put("msg", String.format("不存在该作者 s%", name));
        }
        return jsonObject;
    }

    @RequestMapping("/findAll")
    public JSONObject findAll(String name,Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum ,pageSize);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", "success");
        jsonObject.put("message", "成功");
        jsonObject.put("data", poetriesRepository.findAllByTitle(name, pageable));
        return jsonObject;
    }

    @RequestMapping("/save")
    public JSONObject save(@RequestBody Poets poets) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", "success");
        jsonObject.put("message", "成功");
        jsonObject.put("data", poets);
        return jsonObject;
    }

    @RequestMapping(value = "/show/{id}",method = RequestMethod.POST)
    public JSONObject show(@PathVariable("id") Integer id,String name,@RequestBody Poets poets) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", "success");
        jsonObject.put("message", "成功");
        JSONObject data = new JSONObject();
        data.put("domain", poets);
        data.put("id", id);
        data.put("name", name);
        jsonObject.put("data", data);
        return jsonObject;
    }

    @RequestMapping("/showGet/{id}")
    public JSONObject showGet(@PathVariable("id") Integer id,@RequestParam String name) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", "success");
        jsonObject.put("message", "成功");
        JSONObject data = new JSONObject();
        data.put("id", id);
        data.put("name", name);
        jsonObject.put("data", data);
        return jsonObject;
    }
}
