package op.wawa.opacketfix.features.hytpacket;



import op.wawa.opacketfix.features.hytpacket.packets.GermModPacket;
import op.wawa.opacketfix.features.hytpacket.packets.VexViewPacket;
import op.wawa.opacketfix.utils.ClientUtils;

import java.util.ArrayList;
import java.util.List;

public class PacketManager {
    public final ArrayList<CustomPacket> packets = new ArrayList<>();

    public String getName() {
        return "Packet Manager";
    }

    public void init() {
        ClientUtils.getLogger().info("[HYTPacket] Loading packets...");
        packets.add(new GermModPacket());
        packets.add(new VexViewPacket());
        ClientUtils.getLogger().info("[HYTPacket] Loaded " + packets.size() + " packets.");
    }
}
