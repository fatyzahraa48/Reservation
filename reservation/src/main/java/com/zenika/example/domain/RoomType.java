package com.zenika.example.domain;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum RoomType {
    VC(Arrays.asList(EquipmentType.SCREEN, EquipmentType.OCTOPUS, EquipmentType.WEBCAM)),
    SPEC(Arrays.asList(EquipmentType.BOARD)),
    RS(new ArrayList<>()),
    RC(Arrays.asList(EquipmentType.BOARD, EquipmentType.SCREEN, EquipmentType.OCTOPUS));

    private List<EquipmentType> equipements;

    RoomType(List<EquipmentType> equipements) {
        this.equipements = equipements;
    }

    public List<EquipmentType> getEquipements() {
        return equipements;
    }

    }


