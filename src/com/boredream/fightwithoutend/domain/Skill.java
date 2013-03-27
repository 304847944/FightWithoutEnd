
package com.boredream.fightwithoutend.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ����
 * 
 * @author boredream
 *
 */
public class Skill implements Serializable {

    // ���������Type
    public static final int TYPE_ATTRACT = 11;
    public static final int TYPE_DEFENSE = 12;

    // ���ܾ���Ч��SkillEffect(SE)
    public static final int SE_ATT_HARM_ADDITION = 101; // �˺��ӳ�
    public static final int SE_DEF_BLOCK = 201; // ��

    private int id;
    private String name;
    private int type;
    private int occurProbability; // ��������,�ٷֱ���ֵ���ƴ���
    private int level;
    private int skillEffect; // ���ܾ���Ч��

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public int getOccurProbability() {
        return occurProbability;
    }

    public void setOccurProbability(int occurProbability) {
        this.occurProbability = occurProbability;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getSkillEffect() {
        return skillEffect;
    }

    /**
     * ��ù����ӳ�ָ��
     * 
     * @return
     */
    public int getHarmAdditionValue() {
        if (id == 1) { // ����
            // ����:�ػ��ȼ�Ϊ3,���˺��ӳ�Ϊ 1.0 + 3*1 = 4��
            return 1 + level;
        }
        return 1;
    }

    public Skill(int id, String name, int type, int skillEffect, int occurProbability) {
        super();
        this.id = id;
        this.name = name;
        this.type = type;
        this.occurProbability = occurProbability;
        this.skillEffect = skillEffect;
        this.level = 1;
    }

    /**
     * ��ȡÿһ������sp���ܵ�
     * <p>
     * ���ݵ�ǰ��ͬ�ȼ����ظ��ȼ����輼�ܵ�
     * </p>
     * 
     * @return ��Ҫsp��
     */
    public int getSp4rise() {
        int sp4rise = 2;
        switch (level) {
            case 1:
                sp4rise = 2;
                break;
            case 2:
                sp4rise = 3;
                break;
            case 3:
                sp4rise = 4;
                break;
            case 4:
                sp4rise = 6;
                break;
            case 5:
                sp4rise = 8;
                break;
            case 6:
                sp4rise = 10;
                break;
            case 7:
                sp4rise = 14;
                break;
            case 8:
                sp4rise = 20;
                break;
        }

        return sp4rise;
    }

    public static List<Skill> getAllSkills() {
        List<Skill> allSkills = new ArrayList<Skill>();
        Skill skill1 = new Skill(1, "����", TYPE_ATTRACT, SE_ATT_HARM_ADDITION, 10);
        Skill skill2 = new Skill(2, "��", TYPE_DEFENSE, SE_DEF_BLOCK, 10);
        allSkills.add(skill1);
        allSkills.add(skill2);
        return allSkills;
    }

}
