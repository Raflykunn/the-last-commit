package the.last.commit.models;

public class KatagiriHero extends Hero {
    
    public KatagiriHero(int progressId, String name) {
        super(progressId, name, "katagiri");
        this.baseHp = 650;
        this.baseResource = 450;
        this.resourceName = "Mana";
        this.baseDefense = 5;
        
        this.basicAtkName = "Flame";
        this.baseAtk = 45;
        
        this.skillAtkName = "Glacial Prison";
        this.baseSkillAtk = 140;
        this.skillCost = 85;
        this.skillCd = 9;
        
        this.ultAtkName = "Absolute Zero";
        this.baseUltAtk = 550;
        this.ultCost = 320;
        this.ultCd = 24;
        
        this.imagePath = "/images/Katagiri.png";
        
        this.currentHp = getTotalMaxHp();
        this.currentResource = getTotalMaxResource();
    }
}
