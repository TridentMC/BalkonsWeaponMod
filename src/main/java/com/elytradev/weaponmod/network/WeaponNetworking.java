package com.elytradev.weaponmod.network;

import com.elytradev.concrete.network.NetworkContext;

public class WeaponNetworking {

    public static NetworkContext NETWORK = null;

    public static void init() {
        NETWORK = NetworkContext.forChannel("weaponmod");

        NETWORK.register(MessageExplosion.class);
        NETWORK.register(MessageFireCannon.class);
    }

}
