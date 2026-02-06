package net.crystalix.teleport.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

public record Config(List<UUID> uuids) {

    @JsonCreator
    public Config(@JsonProperty("uuids") List<UUID> uuids) {
        this.uuids = uuids;
    }
}
