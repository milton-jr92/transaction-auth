package service;

import model.MccType;

import java.util.HashMap;
import java.util.Map;

public class MerchantMccMapper {

    private final Map<String, MccType> merchantMccMap;

    public MerchantMccMapper() {
        this.merchantMccMap = new HashMap<>();
        initializeMerchantMccMap();
    }

    private void initializeMerchantMccMap() {
        merchantMccMap.put("UBER TRIP                   SAO PAULO BR", MccType.CASH);
        merchantMccMap.put("PICPAY*BILHETEUNICO           GOIANIA BR", MccType.CASH);
        merchantMccMap.put("UBER EATS                   SAO PAULO BR", MccType.MEAL);
        merchantMccMap.put("PAG*JoseDaSilva          RIO DE JANEI BR", MccType.FOOD);
    }

    public MccType getMccType(String merchant, String mcc) {
        return merchantMccMap.getOrDefault(merchant, MccType.fromMcc(mcc));
    }
}