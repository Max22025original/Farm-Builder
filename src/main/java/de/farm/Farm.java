package de.farm;

import org.bukkit.Material;

record Farm(String name, Material icon, int height, Gen gen) {
    @FunctionalInterface
    interface Gen {
        void build(Builder b);
    }
}
