package me.gurwi.athchunkclaim.objects;

import org.bukkit.Chunk;
import org.bukkit.World;

import java.util.List;
import java.util.UUID;

public class PlayerChunk {

    public UUID claimerUUID;
    public List<UUID> ownersUUID;
    public List<UUID> membersUUID;

    public Chunk chunk;
    public World world;
    public int x;
    public int z;

    public boolean ownerCanBreak;

    public boolean ownerCanPlace;
    public boolean ownerCanInteract;

    public boolean memberCanBreak;
    public boolean memberCanPlace;
    public boolean memberCanInteract;


    public Chunk getChunk() {
        return chunk;
    }

    public void setChunk(Chunk chunk) {
        this.chunk = chunk;
    }
    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public UUID getClaimerUUID() {
        return claimerUUID;
    }

    public void setClaimerUUID(UUID claimerUUID) {
        this.claimerUUID = claimerUUID;
    }

    public List<UUID> getOwnersUUID() {
        return ownersUUID;
    }

    public void setOwnersUUID(List<UUID> ownersUUID) {
        this.ownersUUID = ownersUUID;
    }

    public List<UUID> getMembersUUID() {
        return membersUUID;
    }

    public void setMembersUUID(List<UUID> membersUUID) {
        this.membersUUID = membersUUID;
    }

    public boolean isOwnerCanBreak() {
        return ownerCanBreak;
    }

    public void setOwnerCanBreak(boolean ownerCanBreak) {
        this.ownerCanBreak = ownerCanBreak;
    }

    public boolean isOwnerCanPlace() {
        return ownerCanPlace;
    }

    public void setOwnerCanPlace(boolean ownerCanPlace) {
        this.ownerCanPlace = ownerCanPlace;
    }

    public boolean isOwnerCanInteract() {
        return ownerCanInteract;
    }

    public void setOwnerCanInteract(boolean ownerCanInteract) {
        this.ownerCanInteract = ownerCanInteract;
    }

    public boolean isMemberCanBreak() {
        return memberCanBreak;
    }

    public void setMemberCanBreak(boolean membersCanBreak) {
        this.memberCanBreak = membersCanBreak;
    }

    public boolean isMemberCanPlace() {
        return memberCanPlace;
    }

    public void setMemberCanPlace(boolean memberCanPlace) {
        this.memberCanPlace = memberCanPlace;
    }

    public boolean isMemberCanInteract() {
        return memberCanInteract;
    }

    public void setMemberCanInteract(boolean memberCanInteract) {
        this.memberCanInteract = memberCanInteract;
    }
}
