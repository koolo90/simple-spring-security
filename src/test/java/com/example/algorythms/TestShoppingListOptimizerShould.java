package com.example.algorythms;

import com.example.algorythms.shopping.ShoppingListOptimizer;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TestShoppingListOptimizerShould {
    String productList = """
            płyn do naczyń,chemia,v1
            marchew,owoce i warzywa,v2
            szynka,wędliny,v3
            szampon,kosmetyki,v4
            chleb,pieczywo,v5
            papier toaletowy,artykuły higieniczne,v6
            woda mineralna,napoje,v7
            ryż,produkty sypkie,v8
            pasta do zębów,kosmetyki,v9
            ziemniaki,owoce i warzywa,v10
            jabłka,owoce i warzywa,v10
            kawa,napoje,v11
            masło,nabiał,v12
            ser żółty,nabiał,v12
            makaron,produkty sypkie,v13
            ketchup,przyprawy,v14
            ręczniki papierowe,artykuły higieniczne,v15
            piwo,napoje alkoholowe,v16
            płatki śniadaniowe,produkty śniadaniowe,v17
            mydło w płynie,chemia,v18""";

    @SuppressWarnings("unused") // was hardcoded to create proper assertions in resulting tests
    public static String optimalProductsList = """
            szynka,wędliny,v1
            chleb,pieczywo,v2
            szampon,kosmetyki,v3
            pasta do zębów,kosmetyki,v3
            marchew,owoce i warzywa,v4
            ziemniaki,owoce i warzywa,v4
            jabłka,owoce i warzywa,v4
            masło,nabiał,v5
            ser żółty,nabiał,v5
            ryż,produkty sypkie,v6
            makaron,produkty sypkie,v6
            ketchup,przyprawy,v7
            papier toaletowy,artykuły higieniczne,v8
            ręczniki papierowe,artykuły higieniczne,v8
            woda mineralna,napoje,v9
            kawa,napoje,v9
            piwo,napoje alkoholowe,v10
            płatki śniadaniowe,produkty śniadaniowe,v11
            płyn do naczyń,chemia,v12
            mydło w płynie,chemia,v12""";

    ShoppingListOptimizer shoppingListOptimizer = new ShoppingListOptimizer(productList);

    @Test
    void shouldReturnShoppongListSize() {
        assertThat(shoppingListOptimizer.getListSize()).isEqualTo(20);
    }

    @Test
    void shouldCountDepartments() {
        assertThat(shoppingListOptimizer.departmentsCount()).isEqualTo(12);
    }

    @Test
    void shouldCountInitialRouting() {
        assertThat(shoppingListOptimizer.initialRoutingsCount()).isEqualTo(18);
    }

    @Test
    @Tag("optional")
    void shouldCountRevisitedDeparments() {
        assertThat(shoppingListOptimizer.repeatedRoutinsCount()).isEqualTo(2);
    }

    @Test
    @Tag("featrue")
    void shouldCalculateOptimization() {
        assertThat(shoppingListOptimizer.countOptimization()).isEqualTo(6);
    }

    @Test
    @Tag("deprecated")
    void shouldGetRoutingAsAList() {
        assertThat(shoppingListOptimizer.mapToRouting()).hasSize(18);
    }

    @Test
    @Tag("deprecated")
    void shouldGetOptmizedVisitsAsAList() {
        assertThat(shoppingListOptimizer.optimizeVisits()).hasSize(12);
    }


}