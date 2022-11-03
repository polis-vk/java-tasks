package ru.mail.polis.homework.streams.store;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * РљР»Р°СЃСЃ РґР»СЏ СЂР°Р±РѕС‚С‹ СЃРѕ СЃС‚Р°С‚РёСЃС‚РёРєРѕР№ РїРѕ Р·Р°РєР°Р·Р°Рј РјР°РіР°Р·РёРЅР°.
 * РћС†РµРЅРєР° 5-С‚СЊ Р±Р°Р»Р»РѕРІ
 */
public class StoreStatistic {

    /**
     * Р’РµСЂРЅСѓС‚СЊ СЃРєРѕР»СЊРєРѕ Р±С‹Р»Рѕ РїСЂРѕРґР°РЅРѕ РѕРїСЂРµРґРµР»РµРЅРЅРѕРіРѕ С‚РѕРІР°СЂР° Р·Р° РїРµСЂРµРґР°РЅРЅС‹Р№ РїСЂРѕРјРµР¶СѓС‚РѕРє РІСЂРµРјРµРЅРё
     *
     * @param orders   - СЃРїРёСЃРѕРє Р·Р°РєР°Р·РѕРІ
     * @param typeItem - РІРёРґ С‚РѕРІР°СЂР°
     * @param from     - СЃ РєР°РєРѕРіРѕ РјРѕРјРµРЅС‚Р° РІРµСЃС‚Рё РїРѕРґСЃС‡РµС‚
     * @param to       - РїРѕ РєР°РєРѕР№ РјРѕРјРµРЅС‚ РІРµСЃС‚Рё РїРѕРґСЃС‡РµС‚
     * @return - РєРѕР»-РІРѕ РїСЂРѕРґР°РЅРЅРѕРіРѕ С‚РѕРІР°СЂР°
     */
    public long proceedsByItems(List<Order> orders, Item typeItem, Timestamp from, Timestamp to) {
        return orders
                .stream()
                .filter(order -> order.getTime().compareTo(from) >= 0 && order.getTime().compareTo(to) <= 0)
                .map(Order::getItemCount)
                .mapToLong(t -> t.getOrDefault(typeItem, 0))
                .sum();
    }

    /**
     * Р’РµСЂРЅСѓС‚СЊ СЃС‚Р°С‚РёСЃС‚РёРєСѓ РїРѕ РєР°Р¶РґРѕРјСѓ РґРЅСЋ СЃРєРѕР»СЊРєРѕ РєР°РєРѕРіРѕ С‚РѕРІР°СЂР° Р±С‹Р»Рѕ РїСЂРѕРґР°РЅРЅРѕ
     *
     * @param orders - СЃРїРёСЃРѕРє Р·Р°РєР°Р·РѕРІ
     * @return - map, РіРґРµ РєР»СЋС‡ - РЅР°С‡Р°Р»Рѕ РґРЅСЏ (00:00:00 000 UTC) РґР»СЏ РєРѕС‚РѕСЂРѕРіРѕ СЃРѕР±СЂР°РЅР° СЃС‚Р°С‚РёСЃС‚РёРєР°
     * Р·РЅР°С‡РµРЅРёРµ - map С‚РѕРІР°СЂ/РєРѕР»-РІРѕ
     */
    public Map<Timestamp, Map<Item, Integer>> statisticItemsByDay(List<Order> orders) {
        return orders
                .stream()
                .collect(Collectors.toMap(
                        order -> Timestamp.valueOf(order.getTime().toLocalDateTime().toLocalDate().atStartOfDay()),
                        Order::getItemCount,
                        (o, n) -> {
                            o.forEach((key, value) -> n.merge(key, value, Integer::sum));
                            return n;
                        }
                ));
    }

    /**
     * Р’РµСЂРЅСѓС‚СЊ СЃР°РјС‹Р№ РїРѕРїСѓР»СЏСЂРЅС‹Р№ С‚РѕРІР°СЂ
     *
     * @param orders - СЃРїРёСЃРѕРє Р·Р°РєР°Р·РѕРІ
     * @return - С‚РѕРІР°СЂ
     */
    public Item mostPopularItem(List<Order> orders) {
        return orders
                .stream()
                .flatMap(o -> o.getItemCount().entrySet().stream())
                .max(Map.Entry.comparingByValue())
                .get()
                .getKey();
    }

    /**
     * Р’РµСЂРЅСѓС‚СЊ СЃСѓРјРјСѓ РґР»СЏ 5 СЃР°РјС‹С… Р±РѕР»СЊС€РёС… РїРѕ РєРѕР»-РІСѓ С‚РѕРІР°СЂРѕРІ Р·Р°РєР°Р·Сѓ.
     *
     * @param orders - СЃРїРёСЃРѕРє Р·Р°РєР°Р·РѕРІ
     * @return map - Р·Р°РєР°Р· / РѕР±С‰Р°СЏ СЃСѓРјРјР° Р·Р°РєР°Р·Р°
     */
    public Map<Order, Long> sum5biggerOrders(List<Order> orders) {
        return orders
                .stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        o -> Long.valueOf(o.getItemCount().values().stream().mapToInt(i -> 1).sum())
                ))
                .entrySet()
                .stream()
                .sorted((c1, c2) -> c2.getValue().compareTo(c1.getValue()))
                .limit(5)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        o -> Long.valueOf(o.getKey().getItemCount().entrySet().stream().mapToInt(i -> (int) (i.getKey().getPrice() * i.getValue())).sum())
                ));
    }
}