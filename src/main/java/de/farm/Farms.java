package de.farm;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.List;

/** Alle Farmen und Kategorien. Neue Farm = eine neue Zeile + eine kleine Build-Methode. */
final class Farms {
    private Farms() {}

    static final List<Category> CATEGORIES = List.of(
        new Category("Getreide & Gemüse", Material.WHEAT, List.of(
            new Farm("Weizen-Farm", Material.WHEAT, 3, cropField(Material.WHEAT)),
            new Farm("Karotten-Farm", Material.CARROT, 3, cropField(Material.CARROTS)),
            new Farm("Kartoffel-Farm", Material.POTATO, 3, cropField(Material.POTATOES)),
            new Farm("Rote-Bete-Farm", Material.BEETROOT, 3, cropField(Material.BEETROOTS))
        )),
        new Category("Melonen & Kürbis", Material.MELON_SLICE, List.of(
            new Farm("Melonen-Farm", Material.MELON_SLICE, 3, fruitField(Material.MELON_STEM)),
            new Farm("Kürbis-Farm", Material.PUMPKIN, 3, fruitField(Material.PUMPKIN_STEM))
        )),
        new Category("Zuckerrohr & Bambus", Material.SUGAR_CANE, List.of(
            new Farm("Zuckerrohr-Farm", Material.SUGAR_CANE, 5, Farms::sugarCane),
            new Farm("Bambus-Farm", Material.BAMBOO, 14, Farms::bamboo)
        )),
        new Category("Kaktus", Material.CACTUS, List.of(
            new Farm("Kaktus-Farm", Material.CACTUS, 5, Farms::cactus)
        )),
        new Category("Nether", Material.NETHER_WART, List.of(
            new Farm("Netherwarzen-Farm", Material.NETHER_WART, 3, Farms::netherWart)
        )),
        new Category("Bäume", Material.OAK_SAPLING, List.of(
            new Farm("Eichen-Farm", Material.OAK_SAPLING, 12, trees(Material.OAK_SAPLING)),
            new Farm("Birken-Farm", Material.BIRCH_SAPLING, 12, trees(Material.BIRCH_SAPLING)),
            new Farm("Fichten-Farm", Material.SPRUCE_SAPLING, 12, trees(Material.SPRUCE_SAPLING))
        )),
        new Category("Tiere", Material.LEAD, List.of(
            new Farm("Kuh-Gehege", Material.BEEF, 3, pen(EntityType.COW)),
            new Farm("Schaf-Gehege", Material.WHITE_WOOL, 3, pen(EntityType.SHEEP)),
            new Farm("Schwein-Gehege", Material.PORKCHOP, 3, pen(EntityType.PIG)),
            new Farm("Hühner-Gehege", Material.CHICKEN, 3, pen(EntityType.CHICKEN))
        )),
        new Category("Wasser", Material.KELP, List.of(
            new Farm("Seetang-Farm", Material.KELP, 6, Farms::kelp)
        )),
        new Category("Beeren", Material.SWEET_BERRIES, List.of(
            new Farm("Süßbeeren-Farm", Material.SWEET_BERRIES, 3, Farms::berries)
        )),
        new Category("Pilze", Material.RED_MUSHROOM, List.of(
            new Farm("Brauner-Pilz-Farm", Material.BROWN_MUSHROOM, 4, mushrooms(Material.BROWN_MUSHROOM)),
            new Farm("Roter-Pilz-Farm", Material.RED_MUSHROOM, 4, mushrooms(Material.RED_MUSHROOM))
        ))
    );

    static int totalFarms() {
        return CATEGORIES.stream().mapToInt(c -> c.farms().size()).sum();
    }

    // ---------- Bauanleitungen ----------

    /** Ackerbeete mit Wasserkanal alle 5 Bloecke (Bewaesserung reicht 4 Bloecke weit). */
    private static Farm.Gen cropField(Material crop) {
        return b -> {
            for (int x = -b.h; x <= b.h; x++) {
                for (int z = -b.h; z <= b.h; z++) {
                    if (Math.floorMod(x, 5) == 0) {
                        b.set(x, 0, z, Material.WATER);
                    } else {
                        b.set(x, 0, z, Material.FARMLAND);
                        b.crop(x, 1, z, crop);
                    }
                }
            }
        };
    }

    /** Melone/Kürbis: Wasser | Stiel | freie Erde fuer Frucht | Stiel | (Wasser). */
    private static Farm.Gen fruitField(Material stem) {
        return b -> {
            for (int x = -b.h; x <= b.h; x++) {
                for (int z = -b.h; z <= b.h; z++) {
                    switch (Math.floorMod(x, 4)) {
                        case 0 -> b.set(x, 0, z, Material.WATER);
                        case 2 -> b.set(x, 0, z, Material.GRASS_BLOCK);
                        default -> {
                            b.set(x, 0, z, Material.FARMLAND);
                            b.crop(x, 1, z, stem);
                        }
                    }
                }
            }
        };
    }

    private static void sugarCane(Builder b) {
        for (int x = -b.h; x <= b.h; x++) {
            for (int z = -b.h; z <= b.h; z++) {
                if (Math.floorMod(x, 3) == 0) {
                    b.set(x, 0, z, Material.WATER);
                } else {
                    b.set(x, 0, z, Material.SAND);
                    b.set(x, 1, z, Material.SUGAR_CANE);
                }
            }
        }
    }

    private static void bamboo(Builder b) {
        b.floor(Material.DIRT);
        for (int x = -b.h; x <= b.h; x++)
            for (int z = -b.h; z <= b.h; z++)
                if (Math.floorMod(x, 2) == 0 && Math.floorMod(z, 2) == 0)
                    b.set(x, 1, z, Material.BAMBOO);
    }

    private static void cactus(Builder b) {
        b.floor(Material.SAND);
        for (int x = -b.h; x <= b.h; x++)
            for (int z = -b.h; z <= b.h; z++)
                if (Math.floorMod(x, 3) == 0 && Math.floorMod(z, 3) == 0)
                    b.set(x, 1, z, Material.CACTUS);
    }

    private static void netherWart(Builder b) {
        b.floor(Material.SOUL_SAND);
        for (int x = -b.h; x <= b.h; x++)
            for (int z = -b.h; z <= b.h; z++)
                b.crop(x, 1, z, Material.NETHER_WART);
    }

    private static Farm.Gen trees(Material sapling) {
        return b -> {
            b.floor(Material.GRASS_BLOCK);
            for (int x = -b.h; x <= b.h; x++)
                for (int z = -b.h; z <= b.h; z++)
                    if (Math.floorMod(x, 4) == 0 && Math.floorMod(z, 4) == 0)
                        b.set(x, 1, z, sapling);
        };
    }

    /** Zaun-Gehege mit Tor und ein paar Tieren. */
    private static Farm.Gen pen(EntityType animal) {
        return b -> {
            b.floor(Material.GRASS_BLOCK);
            b.ring(1, 1, Material.OAK_FENCE);
            b.set(0, 1, -b.h, Material.OAK_FENCE_GATE);
            int count = Math.max(4, b.n / 3);
            for (int i = 0; i < count; i++) {
                int x = -b.h + 1 + (i * 3) % (b.n - 2);
                int z = -b.h + 1 + (i * 5) % (b.n - 2);
                b.spawn(animal, x, z);
            }
        };
    }

    /** Wasserbecken mit Seetang. */
    private static void kelp(Builder b) {
        b.floor(Material.SAND);
        b.ring(1, 5, Material.STONE_BRICKS);
        b.fill(-b.h + 1, 1, -b.h + 1, b.h - 1, 4, b.h - 1, Material.WATER);
        for (int x = -b.h + 1; x <= b.h - 1; x++)
            for (int z = -b.h + 1; z <= b.h - 1; z++)
                if (Math.floorMod(x, 2) == 0 && Math.floorMod(z, 2) == 0)
                    b.set(x, 1, z, Material.KELP);
    }

    private static void berries(Builder b) {
        b.floor(Material.GRASS_BLOCK);
        for (int x = -b.h; x <= b.h; x++)
            for (int z = -b.h; z <= b.h; z++)
                if (Math.floorMod(x, 2) == 0 && Math.floorMod(z, 2) == 0)
                    b.crop(x, 1, z, Material.SWEET_BERRY_BUSH);
    }

    /** Dunkler Raum mit Myzel und Pilzen. */
    private static Farm.Gen mushrooms(Material mushroom) {
        return b -> {
            b.floor(Material.MYCELIUM);
            b.ring(1, 3, Material.DARK_OAK_PLANKS);
            b.fill(-b.h, 4, -b.h, b.h, 4, b.h, Material.DARK_OAK_PLANKS);
            b.set(0, 1, -b.h, Material.AIR);
            b.set(0, 2, -b.h, Material.AIR);
            for (int x = -b.h + 1; x <= b.h - 1; x++)
                for (int z = -b.h + 1; z <= b.h - 1; z++)
                    if (Math.floorMod(x, 2) == 0 && Math.floorMod(z, 2) == 0)
                        b.set(x, 1, z, mushroom);
        };
    }
}
