package com.example.algorythms.shopping;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ShoppingListOptimizer {
    private static final String OUTSIDE = "Outside";

    private final Map<String, String> shoppingList;

    public ShoppingListOptimizer(String productList) {
        this.shoppingList = mapToMap(productList);
    }

    private Map<String, String> mapToMap(String productList) {
        return Stream.of(productList.split("\n"))
                .map(p -> p.split(","))
                .collect(Collectors.toMap(
                        p -> p[0],
                        p -> p[1],
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
    }

    public int countOptimization() {
        int suboptimalRoutingCount = initialRoutingsCount();
        int minimumDepartmentVisits = departmentsCount();
        return suboptimalRoutingCount - minimumDepartmentVisits;
    }

    public int repeatedRoutinsCount() {
        int resultRoutings = 0;
        String prevDeparment = OUTSIDE;
        for (String deparmentName : shoppingList.values()) {
            if (prevDeparment.equals(deparmentName)) {
                resultRoutings++;
            }
            prevDeparment = deparmentName;
        }

        return resultRoutings;
    }

    public int initialRoutingsCount() {
        int resultRoutings = 0; //entering the shop is 1
        String prevDeparment = OUTSIDE;
        for (String deparmentName : shoppingList.values()) {
            if (!prevDeparment.equals(deparmentName)) {
                resultRoutings++;
            }
            prevDeparment = deparmentName;
        }

        return resultRoutings;
    }

    public int departmentsCount() {
        return new HashSet<>(shoppingList.values()).size();
    }

    /**
     * @deprecated (01.08.2025, There are more efficient solutions, there is no replacement)
     * @return routList
     */
    @Deprecated(since = "Using collections was not optimal")
    public Map<String, List<String>> optimizeVisits() {
        Set<String> departments = new HashSet<>(shoppingList.values());
        HashMap<String, List<String>> optimalVisits = new LinkedHashMap<>();

        departments.forEach(dpt -> {
            Optional<Map.Entry<String, List<String>>> first = optimalVisits.entrySet().stream()
                    .filter(e -> e.getValue().contains(dpt))
                    .findFirst();

            if (first.isPresent()) {
                optimalVisits.get(dpt).add(dpt);
            } else {
                optimalVisits.put(dpt, new ArrayList<>());
            }

            shoppingList.entrySet().stream().filter(e -> e.getValue().equals(dpt))
                    .forEach(e -> optimalVisits.get(dpt).add(e.getKey()));
        });

        return optimalVisits;
    }

    /**
     * @deprecated (01.08.2025, There are more efficient solutions, please use initialRoutingCount instead)
     * @return routList
     */
    @Deprecated(since = "Using collections was not optimal")
    public List<String> mapToRouting() {
        List<String> routing = new ArrayList<>();
        String prevDeparment = OUTSIDE;
        String prevProduct = "Shopping";
        for (Map.Entry<String, String> value : shoppingList.entrySet()) {
            String e = MessageFormat.format("Route: [{0}:{1}] -> [{2}:{3}]", prevDeparment, prevProduct, value.getValue(), value.getKey());
            if (prevDeparment != null && !prevDeparment.equals(value.getValue())) {
                routing.add(e);
            }
            prevProduct = value.getKey();
            prevDeparment = value.getValue();
        }

        return routing;
    }

    public int getListSize() {
        return shoppingList.size();
    }
}
