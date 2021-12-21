package net.htmlcsjs.htmlTech.item;

import gregtech.api.items.metaitem.MetaItem;

public class HTMetaItems {
    public static MetaItem<?>.MetaValueItem LASER_INSPECTOR;

    public static void init() {
        HTMetaItem item = new HTMetaItem((short) 0);
        item.setRegistryName("ht_metaitem");
    }
}
