package io.github.dug22.cipherlabs.resources;


import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class ResourceManager {

    private static final LinkedHashMap<String, List<Resource>> categorizedResourceMap = new LinkedHashMap<>();

    static {
        categorizedResourceMap.put("Cipher Labs Documentation", getDocumentRelatedResources());
        categorizedResourceMap.put("Cipher Algorithm Resources", getCipherRelatedResources());
    }

    private static List<Resource> getDocumentRelatedResources(){
        return getResourcesByType(ResourceType.DOCUMENTATION);
    }

    private static List<Resource> getCipherRelatedResources(){
        return getResourcesByType(ResourceType.CIPHER);
    }

    private static List<Resource> getResourcesByType(ResourceType resourceType){
        return Arrays.stream(Resource.values())
                       .filter(resource -> resource.getResourceType() == resourceType)
                       .toList();
    }

    public static String findResource(String resourceName) {
        return Arrays.stream(Resource.values())
                .filter(resource -> resource.getResourceName().equalsIgnoreCase(resourceName))
                .map(Resource::getResourceHtmlArticle)
                .findFirst()
                .orElse(null);
    }

    public static LinkedHashMap<String, List<Resource>> getCategorizedResourceMap() {
        return categorizedResourceMap;
    }
}
