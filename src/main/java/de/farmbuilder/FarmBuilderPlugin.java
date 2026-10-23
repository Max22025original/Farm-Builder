package de.farmbuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.*;

public final class FarmBuilderPlugin extends JavaPlugin implements Listener, CommandExecutor {
 private NamespacedKey key;
 private final LinkedHashMap<String,List<String>> cats=new LinkedHashMap<>();
 public void onEnable(){
  key=new NamespacedKey(this,"action"); seed();
  getCommand("farm").setExecutor(this); getServer().getPluginManager().registerEvents(this,this);
  getLogger().warning("Prototyp: /farm-Menü funktioniert, die Farm-Automation ist noch nicht implementiert.");
 }
 private void seed(){
  cats.put("Pflanzen",Arrays.asList("Weizen","Karotten","Kartoffeln","Rote Bete","Melonen","Kürbisse","Zuckerrohr","Kaktus","Bambus","Seetang","Süßbeeren","Leuchtbeeren","Kakao","Pilze","Moos"));
  cats.put("Tiere",Arrays.asList("Kühe","Hühner","Schweine","Schafe","Hasen","Ziegen","Eier","Honig","Honigwaben","Fische"));
  cats.put("Holz",Arrays.asList("Eiche","Fichte","Birke","Tropenbaum","Schwarzeiche","Akazie","Mangrove","Kirsche","Blasseiche","Stöcke und Blätter"));
  cats.put("Mobs",Arrays.asList("Creeper","Schießpulver","Zombies","Skelette","Spinnen","Höhlenspinnen ohne Spawner","Hexen","Schleime","Enderman-XP","Magmawürfel","Ghast-Tränen","Phantomhaut","Silberfische ohne Spawner","Ertrunkene","Witherskelette"));
  cats.put("Dörfer",Arrays.asList("Eisengolems","Handelshalle","Dorfbewohner-Zucht","Erntehelfer","Katzen","Schneegolems","Schnee","Froschlichter","Schildkröteneier","Hornplatten"));
  cats.put("Nether",Arrays.asList("Gold","Netherquarz","Netherwarzen","Seelensand","Basalt","Schwarzstein","Glowstone","Karmesinwurzeln","Wirrwurzeln","Netherpilze"));
  cats.put("Blöcke",Arrays.asList("Bruchstein","Steingenerator","Glatter Stein","Ton","Tropfstein","Amethyst","Obsidian","Beton","Kies","Sand","Glas","Steinziegel","Schleimblöcke","Honigblöcke","Basaltgenerator"));
  cats.put("Ressourcen",Arrays.asList("Redstone","Knochen","Knochenmehl","Fäden","Pfeile","Leder","Federn","Tintensäcke","Leuchttintensäcke","Prismarinscherben","Prismarinkristalle","Nautilusschalen","Raketen Flugzeit 3","Papier","Automatischer Komposter"));
  cats.put("Extra",Arrays.asList("Kelp-Erfahrung","Bienenstock-Ernte","Tropfblatt","Lava aus Kesseln","Schneebälle","Wächter","Großer Wächter","Enderperlen","Shulker-Schalen","Endstein","Chorusfrucht","Frösche","Kaulquappen","Axolotl","Tropenfische","Kugelfische","Lachs","Kabeljau","Seegurken","Sortieranlage","Automatische Schmelze","Automatische Brauerei","XP-Sammelstation","Mob-Beute-Sortierer","Baumsetzlinge","Blumen","Farbstoffe","Seerosen","Farn","Tote Büsche","Netherholz","Kaktus-XP","Bambus-Brennstoff","Zuckerrohr-Papier","Melonen-Scheiben","Kürbiskerne","Samen-Sortierer","Beeren-Nachschub","Farbblumen","Schneeblock-Ausgabe","Fisch-Sortierer","Eier-Sortierer","Feder-Nachschub","Leder-Nachschub","Faden-Nachschub","Mob-Drops-Sortierer","Ressourcen-Sortierer","Kisten-Sortierer","Ofen-Sortierer","Holzkohle","Lava-Nachschub","Stein-Nachschub","Glas-Nachschub","Ziegel-Nachschub","Komposter-Ausgabe","Automatisches Lager","Wolle nach Farben","Tintenfarm","Eisen-Nachschub","Gold-Nachschub","Kohle-Nachschub","Bienenwachs","Lager-Nachschub","Papier-Nachschub","Knochenmehl-Ausgabe","Netherziegel","Lohenruten","Drachenatem","Purpurblöcke","Kelpblöcke","Honigflaschen","Schleimball-Sortierung","Baumfarm-Mix","Kürbis-Sortierung","Melonen-Sortierung","Pilz-Zutaten","Netherwarzen-Nachschub","Seelensand-Nachschub","Pfeil-Nachschub","Brot-Zutaten","Kuchen-Zutaten","Goldene-Karotten-Zutaten","Trank-Zutaten","Raketen-Zutaten","Samen-Nachschub","Dünger-Nachschub","Allgemeine Pflanzenfarm","Allgemeine Mobfarm","Allgemeine Materialfarm"));
 }
 public boolean onCommand(CommandSender s,Command c,String l,String[] a){if(!(s instanceof Player)){s.sendMessage("Bitte im Spiel als Spieler nutzen.");return true;}open((Player)s);return true;}
 private void open(Player p){
  Inventory inv=Bukkit.createInventory(null,27,ChatColor.DARK_GREEN+"FarmBuilder");
  Material[] icons={Material.WHEAT,Material.COW_SPAWN_EGG,Material.OAK_LOG,Material.ROTTEN_FLESH,Material.IRON_INGOT,Material.NETHERRACK,Material.COBBLESTONE,Material.REDSTONE,Material.CHEST};
  int i=0; for(String cat:cats.keySet()){ItemStack it=new ItemStack(icons[i%icons.length]);ItemMeta m=it.getItemMeta();m.setDisplayName(ChatColor.GREEN+cat);m.setLore(Arrays.asList(ChatColor.GRAY+cats.get(cat).size()+" Katalogeinträge",ChatColor.YELLOW+"Klicken"));m.getPersistentDataContainer().set(key,PersistentDataType.STRING,"cat:"+cat);it.setItemMeta(m);inv.setItem(new int[]{10,11,12,13,14,15,16,19,20}[i],it);i++;}
  ItemStack book=new ItemStack(Material.BOOK);ItemMeta bm=book.getItemMeta();bm.setDisplayName(ChatColor.RED+"Prototyp");bm.setLore(Arrays.asList("Die 100 Einträge sind ein Katalog.","Farmen sind noch nicht automatisiert."));book.setItemMeta(bm);inv.setItem(26,book);p.openInventory(inv);
 }
 private void category(Player p,String cat){List<String> list=cats.get(cat);Inventory inv=Bukkit.createInventory(null,54,ChatColor.DARK_GREEN+"FarmBuilder: "+cat);int slot=0;for(String name:list){if(slot>=45)break;ItemStack it=new ItemStack(Material.PAPER);ItemMeta m=it.getItemMeta();m.setDisplayName(ChatColor.YELLOW+name);m.setLore(Arrays.asList(ChatColor.RED+"Farm-Funktion noch nicht implementiert"));m.getPersistentDataContainer().set(key,PersistentDataType.STRING,"info:"+name);it.setItemMeta(m);inv.setItem(slot++,it);}p.openInventory(inv);}
 @EventHandler public void click(InventoryClickEvent e){if(!(e.getWhoClicked() instanceof Player))return;if(!e.getView().getTitle().startsWith(ChatColor.DARK_GREEN+"FarmBuilder"))return;e.setCancelled(true);ItemStack it=e.getCurrentItem();if(it==null||!it.hasItemMeta())return;String a=it.getItemMeta().getPersistentDataContainer().get(key,PersistentDataType.STRING);if(a==null)return;Player p=(Player)e.getWhoClicked();if(a.startsWith("cat:"))category(p,a.substring(4));else if(a.startsWith("info:"))p.sendMessage(ChatColor.RED+"Diese Farm ist noch nicht automatisiert; dies ist ein Katalog-Prototyp.");}
}
