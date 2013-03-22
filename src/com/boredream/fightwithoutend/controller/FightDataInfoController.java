
package com.boredream.fightwithoutend.controller;

import android.util.Log;

import com.boredream.fightwithoutend.domain.FightOneKickData;
import com.boredream.fightwithoutend.domain.FightOneturnData;
import com.boredream.fightwithoutend.domain.Hero;
import com.boredream.fightwithoutend.domain.Monster;
import com.boredream.fightwithoutend.domain.Skill;
import com.boredream.fightwithoutend.domain.Treasure;

import java.util.List;

public class FightDataInfoController {
    private static final String TAG = "FightDataInfoController";

    public static Hero hero = Hero.initHero();

    public static final int TYPE_H2M = 1;
    public static final int TYPE_M2H = 2;
    public static final int TYPE_OVER = 3;

    // 1��������, 2�����﹥��, 3ս�����
    public static int type = TYPE_H2M;

    public static int h2mHarm = 0;
    public static int m2hHarm = 0;
    public static boolean heroIsWin = false;

    public static FightOneturnData runOneTurn(Monster monster) {

        Log.i(TAG, "runOneTurn(" + monster + ")");

        type = TYPE_H2M;

        monster.initHp();
        hero.initHp();

        FightOneturnData oneturnData = new FightOneturnData(monster);
        FightOneKickData oneKickData;
        String oneKickInfo = "";
        while (type != TYPE_OVER) {
            oneKickData = new FightOneKickData();
            if (type == TYPE_H2M) {
                h2mHarm = hero.getAttackValue() - monster.getDefenseValue();
                if (ProbabilityEventController.triggerSkill(hero.getCurrentAttSkill())) {
                    if (hero.getCurrentAttSkill().getSkillEffect() == Skill.SE_ATT_HARM_ADDITION) {
                        h2mHarm *= hero.getCurrentAttSkill().getHarmAdditionValue();
                    }
                    Log.i(TAG, "�����˱���,��� " + h2mHarm + "���˺�(" +
                            hero.getCurrentAttSkill().getHarmAdditionValue() + "��)");
                }
                if (h2mHarm < 0) {
                    h2mHarm = 0;
                }
                oneKickInfo = "->\"" + hero.getName() + "\"������\"����:" + monster.getName() +
                                "\",����� " + h2mHarm + " ���˺�";
                monster.setHp(monster.getHp() - h2mHarm);

                if (monster.getHp() <= 0) {
                    type = TYPE_OVER;
                    oneKickInfo += "\n\"����:" + monster.getName()
                            + "\"��������Ϊ0,��ʤ����!";
                    oneturnData.setFightOutcome(FightOneturnData.FIGHT_OUTCOME_HERO_IS_WIN);
                    List<Treasure> dropTreasures = ProbabilityEventController.dropTreasure(monster);
                    oneturnData.setDropTreasures(dropTreasures);
                    pickTreasures(dropTreasures);
                } else {
                    type = TYPE_M2H;
                }

                // newһ��"һ�λ�����Ϣ"��bean
                oneKickData.setDescribe(oneKickInfo);
                oneKickData.setHarmValue(h2mHarm);
                oneKickData.setHarmType(FightOneKickData.H2M);
                oneKickData.setHeroCurrentHp(hero.getHp());
                oneKickData.setMonsterCurrentHp(monster.getHp());

            } else if (type == TYPE_M2H) {
                m2hHarm = monster.getAttackValue() - hero.getDefenseValue();
                if (m2hHarm < 0) {
                    m2hHarm = 0;
                }
                oneKickInfo = "<-\"����:" + monster.getName() + "\"������\"" + hero.getName() +
                                "\",����� " + m2hHarm + " ���˺�";
                hero.setHp(hero.getHp() - m2hHarm);

                if (hero.getHp() <= 0) {
                    type = TYPE_OVER;
                    oneKickInfo += "\n��������Ϊ0,\"" + hero.getName()
                            + "\"�˽�!";
                    oneturnData.setFightOutcome(FightOneturnData.FIGHT_OUTCOME_MONSTER_IS_WIN);
                } else {
                    type = TYPE_H2M;
                }

                // newһ��"һ�λ�����Ϣ"��bean
                oneKickData.setDescribe(oneKickInfo);
                oneKickData.setHarmValue(m2hHarm);
                oneKickData.setHarmType(FightOneKickData.M2H);
                oneKickData.setHeroCurrentHp(hero.getHp());
                oneKickData.setMonsterCurrentHp(monster.getHp());
            }
            oneturnData.oneKickData.add(oneKickData);
        }
        expRise(oneturnData);
        return oneturnData;
    }

    public static void equip(Treasure treasure) {
        if (treasure.getEquipLocation() == Treasure.EQUIP_LOCATION_WEAPON) {
            // ��ǰװ���ǿ�ʱ,�Ƚ���ǰװ���ƻ���Ʒ��,�ѵ�ǰװ�����
            if (hero.currentWeapon != null) {
                Log.i(TAG, "equip(" + treasure + ") -- ж������");
                hero.totalObtainTreasure.add(hero.currentWeapon);
                hero.currentWeapon = null;
            }
            // �ٽ��µ�װ������,����ǰװ��Ϊ��ʱ,��ֱ���������Ϲ���
            hero.totalObtainTreasure.remove(treasure);
            hero.currentWeapon = treasure;
            Log.i(TAG, "equip(" + treasure + ") -- װ��������");
        } else {
            // ��ǰװ���ǿ�ʱ,�Ƚ���ǰװ���ƻ���Ʒ��,�ѵ�ǰװ�����
            if (hero.currentArmor != null) {
                Log.i(TAG, "equip(" + treasure + ") -- ж�·���");
                hero.totalObtainTreasure.add(hero.currentArmor);
                hero.currentArmor = null;
            }
            // �ٽ��µ�װ������,����ǰװ��Ϊ��ʱ,��ֱ���������Ϲ���
            hero.totalObtainTreasure.remove(treasure);
            hero.currentArmor = treasure;
            Log.i(TAG, "equip(" + treasure + ") -- װ���Ϸ���");
        }
    }

    /**
     * ����Ӣ��ĳ�����ܵĵȼ�
     * 
     * @param skill ��Ҫ�����ļ���
     * @return ������ļ��ܵȼ�
     */
    public static int riseHeroSkill(Skill skill) {
        int skillLevel = skill.getLevel();
        if (hero.sp >= skill.getSp4rise()) {
            hero.sp -= skill.getSp4rise();
            skill.setLevel(skillLevel + 1);
        }
        return skillLevel;
    }

    /**
     * װ��ǿ���������ݴ�����
     * 
     * @param treasure
     * @return
     */
    public static int riseTreasureStar(Treasure treasure) {

        int riseStarResult = ProbabilityEventController.riseTreasureStar(treasure);
        switch (riseStarResult) {
            case Treasure.RISE_STAR_SUCCESS:
                treasure.setStar(treasure.getStar() + 1);
                break;
            case Treasure.RISE_STAR_BREAK:
                hero.totalObtainTreasure.remove(treasure);
                break;
            case Treasure.RISE_STAR_NO_CHANGE:
                // nothing
                Log.i(TAG, "rise star - no change");
                break;

            default:
                break;
        }

        return riseStarResult;
    }

    /**
     * ��ȡ����
     * 
     * @param dropTreasure
     */
    private static void pickTreasures(List<Treasure> dropTreasures) {

        // �����ﶫ����50ʱ,���ټ�ȡ
        if (hero.totalObtainTreasure != null
                && hero.totalObtainTreasure.size() > Hero.MAX_GOODS_COUNT) {
            return;
        }

        if (!heroIsWin) {
            return;
        }

        if (dropTreasures.size() >= 2) {
            Log.i(TAG, "һ�ε���2�����ϵ���");
        }
        for (Treasure treasure : dropTreasures) {
            // ���Ӣ��ʤ����,���Ұ�����û���������,���ȡ
            if (!containsTreasures(hero.totalObtainTreasure, treasure)) {
                hero.totalObtainTreasure.add(treasure);
                Log.i(TAG, "��ȡ����:" + treasure.getName());
            }
        }

    }

    /**
     * �������Ƿ��Ѿ������˴˱���
     * 
     * @param treasureGaint �����ڵı���
     * @param dropTreasures ��Ҫ����Ƿ����е�Ŀ�걦��
     * @return
     */
    private static boolean containsTreasures(List<Treasure> treasureGaint, Treasure targetTreasures) {

        for (Treasure treasure : treasureGaint) {
            if (treasure.getId() == targetTreasures.getId()) {
                return true;
            }
        }
        return false;
    }

    /**
     * ��������
     * 
     * @param oneturnData һ��ս����Ϣ
     */
    private static void expRise(FightOneturnData oneturnData) {
        int expGaint = oneturnData.getExpGaint();
        hero.exp += expGaint;
        Log.i(TAG, "��þ���:" + expGaint);
        checkLevelRise();
    }

    /**
     * ���鵱ǰ�����Ƿ��㹻����
     */
    private static void checkLevelRise() {
        // �����㹻,����
        if (hero.exp >= hero.currentLevelNeedExp()) {
            hero.exp -= hero.currentLevelNeedExp();
            hero.level++;
            hero.sp += Hero.SP_RISE;
            Hero.MAX_HP += Hero.MAX_HP_RISE;
            hero.setAttackValue(hero.getAttackValue() + Hero.ATR_RISE);
            hero.setDefenseValue(hero.getDefenseValue() + Hero.DEF_RISE);
            Log.i(TAG, "����! �ȼ�:" + (hero.level - 1) + "->" + hero.level);
            Log.i(TAG, "����! Ѫ����:" + (Hero.MAX_HP - Hero.MAX_HP_RISE)
                    + "->" + Hero.MAX_HP);
            Log.i(TAG, "����! ������:" + (hero.getAttackValue() - Hero.ATR_RISE)
                    + "->" + hero.getAttackValue());
            Log.i(TAG, "����! ������:" + (hero.getDefenseValue() - Hero.DEF_RISE)
                    + "->" + hero.getDefenseValue());
        }
    }

}
