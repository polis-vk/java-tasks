package ru.mail.polis.homework.io.objects;

import java.util.*;

public enum Ability {
    FLY,
    SWIM,
    RUN,
    JUMP,
    CRAWL,
    CLIMB,
    SPEAK;

    static List<Ability> randomListOfAbilities() {
        Random random = new Random(System.currentTimeMillis());
        int numberOfAbilities = random.nextInt(values().length);
        Set<Integer> randomed = new HashSet<>();
        List<Ability> res = new ArrayList<>();
        for (int i = 0; i < numberOfAbilities; i++) {
            int n;
            do {
                n = random.nextInt(values().length);
            }
            while (randomed.contains(n));
            randomed.add(n);
            res.add(values()[n]);
        }
        return res;
    }
}
