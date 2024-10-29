package com.canifa.stylenest.config;

import org.springframework.stereotype.Component;

import java.util.HashMap;
@Component
public class PathRouting {
    private HashMap<String, Long> PathCategoryIDMapping = new HashMap<>();
    public PathRouting(){
        PathCategoryIDMapping = new HashMap<>();
        PathCategoryIDMapping.put("nam/ao-phong", 4571L);
        PathCategoryIDMapping.put("nam/ao-polo", 4572L);
        PathCategoryIDMapping.put("nam/quan-shorts", 4563L);
        PathCategoryIDMapping.put("nam/quan-kaki", 1042L);
        PathCategoryIDMapping.put("nam/quan-jeans", 4561L);
        PathCategoryIDMapping.put("nam/ao", 4556L);
        PathCategoryIDMapping.put("nam/quan", 4562L);
        PathCategoryIDMapping.put("nam/do-mac-trong", 4567L);
        PathCategoryIDMapping.put("nam/bo-mac-nha", 4584L);
        PathCategoryIDMapping.put("nam/", 5046L);

        PathCategoryIDMapping.put("nu/", 5045L);
        PathCategoryIDMapping.put("nu/bo-mac-nha", 4619L);
        PathCategoryIDMapping.put("nu/ao-phong", 4606L);
        PathCategoryIDMapping.put("nu/vay-lien", 4624L);
        PathCategoryIDMapping.put("nu/ao-kieu", 4721L);
        PathCategoryIDMapping.put("nu/quan-shorts", 4597L);
        PathCategoryIDMapping.put("nu/quan-jeans", 4595L);
        PathCategoryIDMapping.put("nu/do-lot", 1173L);
        PathCategoryIDMapping.put("nu/ao", 4590L);
        PathCategoryIDMapping.put("nu/quan", 4596L);
        PathCategoryIDMapping.put("nu/vay", 4598L);
    }

    public Long getCategoryId(String path){
        return PathCategoryIDMapping.get(path);
    }
}
