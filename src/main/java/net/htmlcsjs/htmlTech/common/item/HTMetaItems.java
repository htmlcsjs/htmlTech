package net.htmlcsjs.htmlTech.common.item;

import gregtech.api.items.metaitem.MetaItem;

public class HTMetaItems {
    public static MetaItem<?>.MetaValueItem LASER_INSPECTOR;
    public static MetaItem<?>.MetaValueItem EMPTY_LASER;
    public static MetaItem<?>.MetaValueItem LASER_GUN;

    public static void init() {
        HTMetaItem item = new HTMetaItem((short) 0);
        item.setRegistryName("ht_metaitem");
    }
}
