package se.lars.uppgB.model;

import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

class EntityTest {

    @org.junit.jupiter.api.Test
    void takeHitTest() {
        Burglar burglar = new Burglar(12,4,"Burje");
        Resident resident = new Resident(12,3,"Rese");
        burglar.takeHit(4);
        Assertions.assertEquals(8,burglar.getHealth());
    }

    @org.junit.jupiter.api.Test
    void punchTest() {
        Burglar burglar = new Burglar(12,4,"Burje");
        Resident resident = new Resident(12,3,"Rese");
        resident.punch(burglar);
        Assertions.assertEquals(9,burglar.getHealth());
        Assertions.assertEquals(12,resident.getHealth());
    }

    @org.junit.jupiter.api.Test
    void isConscious() {
        Resident resident = new Resident(12,3,"Rese");
        resident.takeHit(11);
        Assertions.assertTrue(resident.isConscious());
        resident.takeHit(1);
        Assertions.assertFalse(resident.isConscious());
    }

    @org.junit.jupiter.api.Test
    void addDamage() {
    }
}