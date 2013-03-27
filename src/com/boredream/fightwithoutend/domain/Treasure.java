
package com.boredream.fightwithoutend.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ����
 * 
 * @author boredream
 */
public class Treasure implements Serializable {

    public static final int EQUIP_LOCATION_WEAPON = 1; // ����
    public static final int EQUIP_LOCATION_ARMOR = 2; // ����

    public static final int RISE_STAR_SUCCESS = 11;
    public static final int RISE_STAR_BREAK = 12;
    public static final int RISE_STAR_NO_CHANGE = 13;

    private static List<Treasure> allTreasures;

    private int id;
    private String name;
    private int attAddition;// ��ֵʱ��Ϊ����
    private int defAddition;// ��ֵʱ��Ϊ����
    private int equipLocation;
    private int dropProbability;
    private int star;// װ������

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAttAddition() {
        return attAddition;
    }

    public void setAttAddition(int attAddition) {
        this.attAddition = attAddition;
    }

    public int getDefAddition() {
        return defAddition;
    }

    public void setDefAddition(int defAddition) {
        this.defAddition = defAddition;
    }

    public int getDropProbability() {
        return dropProbability;
    }

    public void setDropProbability(int dropProbability) {
        this.dropProbability = dropProbability;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getStarRiseProbility() {
        int probility = 0;
        switch (star) {
            case 1:
                probility = 80;
                break;
            case 2:
                probility = 70;
                break;
            case 3:
                probility = 50;
                break;
            case 4:
                probility = 30;
                break;
            case 5:
                probility = 15;
                break;
            case 6:
                probility = 5;
                break;
            default:
                probility = 0;
                break;
        }
        return probility;
    }

    /**
     * ��ȡװ��λ��
     * 
     * @return EQUIP_LOCATION_WEAPON = 1 ����; EQUIP_LOCATION_ARMOR = 2 ����
     */
    public int getEquipLocation() {
        if (attAddition > defAddition) {
            equipLocation = EQUIP_LOCATION_WEAPON;
        } else {
            equipLocation = EQUIP_LOCATION_ARMOR;
        }
        return equipLocation;
    }

    public Treasure(int id, String name, int attAddition, int defAddition, int dropProbability) {
        super();
        this.id = id;
        this.name = name;
        this.attAddition = attAddition;
        this.defAddition = defAddition;
        this.dropProbability = dropProbability;
        this.star = 1;
    }

    @Override
    public String toString() {
        return "Treasure [id=" + id + ", name=" + name + ", attAddition=" + attAddition
                + ", defAddition=" + defAddition + ", dropProbability=" + dropProbability + "]";
    }

    /**
     * ���ñ������ҵ���Ӧ����
     * 
     * @param id
     * @return
     */
    public static Treasure findTreasureById(int id) {
        if (allTreasures == null) {
            allTreasures = getAllTreasures();
        }
        for (Treasure treasure : allTreasures) {
            if (treasure.getId() == id) {
                return treasure;
            }
        }
        return null;
    }

    /**
     * ��ȡ���б���
     * 
     * @return
     */
    private static List<Treasure> getAllTreasures() {
        // ���ʰ��ٷ����ƶ�
        allTreasures = new ArrayList<Treasure>();
        allTreasures.add(new Treasure(1, "�޵�С���", 0, 1, 20));
        allTreasures.add(new Treasure(2, "���ܵ�С��ͷ", 1, 0, 20));
        allTreasures.add(new Treasure(3, "��������֮��", 2, 0, 5));
        allTreasures.add(new Treasure(4, "ëë���С�̶�ë", 0, 2, 10));
        allTreasures.add(new Treasure(5, "ëë����޵д�����", 5, 0, 2));
        return allTreasures;
    }

}
