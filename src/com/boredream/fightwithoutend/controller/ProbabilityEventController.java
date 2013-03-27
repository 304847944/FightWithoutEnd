
package com.boredream.fightwithoutend.controller;

import com.boredream.fightwithoutend.domain.Monster;
import com.boredream.fightwithoutend.domain.Skill;
import com.boredream.fightwithoutend.domain.Treasure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * �����¼�������
 * 
 * @author boredream
 */
public class ProbabilityEventController {

    private static Random random = new Random();

    private static int MAX_PROBABILITY = 100;

    /**
     * �����¼����ʿ���
     * 
     * @param currentAllMonsters ��ǰ�������������й���
     * @return �����ĸ�����
     */
    public static Monster encounterNewMonster(ArrayList<Monster> currentAllMonsters) {
        int totalEncounterPros = 0;
        List<Monster> totalMons = new ArrayList<Monster>();
        for (Monster monster : currentAllMonsters) {
            for (int i = 0; i < monster.getEncounterProbability(); i++) {
                totalMons.add(monster);
            }
            totalEncounterPros += monster.getEncounterProbability();
        }
        return totalMons.get(random.nextInt(totalMons.size()));
    }

    /**
     * ��������¼����ʿ���
     * 
     * @param monster �����Ĺ���
     * @return �����ı��Ｏ��
     */
    public static List<Treasure> dropTreasure(Monster monster) {
        List<Treasure> possibleDropTreasures = monster.getPossibleDropTreasure();
        List<Treasure> realDropTreasures = new ArrayList<Treasure>();
        for (Treasure treasure : possibleDropTreasures) {
            int dropProbability = treasure.getDropProbability();
            if (random.nextInt(MAX_PROBABILITY) + 1 <= dropProbability) {
                realDropTreasures.add(treasure);
            }
        }
        return realDropTreasures;
    }

    /**
     * ���������¼����ʿ���
     * 
     * @return trueΪ����
     */
    public static boolean triggerSkill(Skill skill) {
        if (random.nextInt(MAX_PROBABILITY) + 1 <= skill.getOccurProbability()) {
            return true;
        }
        return false;
    }

    /**
     * װ�������¼����ʿ���
     * 
     * @param treasure
     * @return RISE_STAR_SUCCESS �ɹ�; ;
     * @return RISE_STAR_BREAK ����һ��(star>=4ʱ����)
     * @return RISE_STAR_NO_CHANGE �ޱ仯
     */
    public static int riseTreasureStar(Treasure treasure) {
        int result = Treasure.RISE_STAR_NO_CHANGE;
        int starRiseProbility = treasure.getStarRiseProbility();
        if (random.nextInt(MAX_PROBABILITY) + 1 <= starRiseProbility) {
            result = Treasure.RISE_STAR_SUCCESS;
        } else {
            if (treasure.getStar() < 4) {
                result = Treasure.RISE_STAR_NO_CHANGE;
            } else {
                result = Treasure.RISE_STAR_BREAK;
            }
        }
        return result;
    }

}
