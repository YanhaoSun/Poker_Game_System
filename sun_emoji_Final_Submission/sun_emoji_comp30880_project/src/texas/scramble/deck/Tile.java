package texas.scramble.deck;

import texas.scramble.hand.HandElement;

public record Tile(String name, int value) implements HandElement {
    @Override
    public String toString() {
        if (name.equals(" ")) {
            return "^";
        }
        return name;
    }
    @Override
    public int getValue(){
        return this.value;
    }
}