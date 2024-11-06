package se.lars.uppgB.model;

public abstract class Entity {
    int health, damage;
    String role;

    public Entity(int health, int damage, String role) {
        this.health = health;
        this.damage = damage;
        this.role = role;
    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public String getRole() {
        return role;
    }

    public void takeHit(int damage) {
        //minska denna varelses hälsa med värdet av parametern damage
        health -= damage;
    }

    public void punch(Entity toPunch) {
        //attackera det Creature-objekt som är parameter till denna metod
        toPunch.takeHit(this.damage);
    }

    public boolean isConscious() {
        //returnera sant om hälsan är över 0, returnera falskt annars
        if (this.getHealth() <= 0) return false;
        return true;
    }

    public void addDamage(int damage){
        this.damage += damage;
    }

}

