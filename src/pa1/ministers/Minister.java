package pa1.ministers;


import pa1.City;
import pa1.Cost;
import pa1.GameMap;
import pa1.Player;
import pa1.exceptions.TooPoorException;
import pa1.technologies.Technology;

import java.util.List;

/**
 * An abstract class that represents a minister in the game.
 * All actions in the game are done through ministers.
 * Therefore this class will contain the bulk of your implementation of the game.
 */
public abstract class Minister {

    // Attributes
    protected final int intelligence, experience, leadership;

    private boolean isReady = false;

    /**
     * Initializes the attributes of a minister
     *
     * @param intelligence
     * @param experience
     * @param leadership
     */
    protected Minister(int intelligence, int experience, int leadership) {
        this.intelligence = intelligence;
        this.experience = experience;
        this.leadership = leadership;
    }

    /**
     * @return Whether or not the minister is ready
     */
    public boolean isReady() {
        // TODO
        return isReady;
    }

    /**
     * Changes isReady to true
     */
    public void beginTurn() {
        // TODO
        this.isReady = true;
    }

    /**
     * Changes isReady to false
     */
    public void endTurn() {
        // TODO
        this.isReady = false;
    }


    /**
     * Collect gold from a city
     * amount collected = (city population + minister experience + minister leadership) * (# of banks  + 1) * 0.2
     *
     * @param city to collect gold from
     * @return amount of gold collected
     */
    public int collectTax(City city) {
        // TODO
        int amtCollected = (int)((city.getPopulation() + this.experience + this.leadership) * (city.getBanks() + 1) * 0.2);
        return amtCollected;
    }

    /**
     * Collect science points from a city
     * amount collected = (city population + minister experience + minister intelligence) * (# of universities  + 1) * 0.2
     *
     * @param city to collect science points from
     * @return amount of science points collected
     */
    public int collectSciencePoints(City city) {
        // TODO
        int amtCollected = (int)((city.getPopulation() + this.experience + this.intelligence) * (city.getUniversities() + 1)*0.2);
        return amtCollected;
    }


    /**
     * Collect production points from a city
     * amount collected = (city population + minister intelligence + minister leadership) * (# of roads  + 1) * 0.2
     *
     * @param city to collect production points from
     * @return amount of production points collected
     */
    public int collectProductionPoints(City city) {
        // TODO
        int amtCollected = (int)((city.getPopulation() + this.intelligence + this.leadership) * (city.getRoads() + 1)*0.2);
        return amtCollected;
    }

    /**
     * Build a bank in the city
     * 1. Get the cost of building a bank, with applied minister discount
     * 2. Check whether player has enough gold and production points
     * 3. If not, throw an exception
     * 4. Subtract the cost from the player's gold and production point
     * 5. Add number of bank in the city by one
     * <p>
     * HINT:
     * - the Cost class has a getDiscountedCost() method
     * - the Minister class has a getImprovementDiscountRate() method, to obtain the
     * discount rate of building a bank
     *
     * @param player
     * @param city
     * @throws TooPoorException
     */
    public void buildBank(Player player, City city) throws TooPoorException {
        // TODO
        Cost buildingBankCost = city.getBankCost();
        Cost discountedBuildingBankCost = buildingBankCost.getDiscountedCost(this.getImprovementDiscountRate());
        if(player.getGold() >= discountedBuildingBankCost.getGold() && player.getProductionPoint() >= discountedBuildingBankCost.getProduction()){
            player.decreaseGold(discountedBuildingBankCost.getGold());
            player.decreaseProductionPoint(discountedBuildingBankCost.getGold());
            city.addBank();
        }
        else{
            throw new TooPoorException(player, discountedBuildingBankCost);
        }
    }

    /**
     * Build a road in the city
     * 1. Get the cost of building a road, with applied minister discount
     * 2. Check whether player has enough gold and production points
     * 3. If not, throw an exception
     * 4. Subtract the cost from the player's gold and production point
     * 5. Add number of road in the city by one
     * <p>
     * HINT:
     * - the Cost class has a getDiscountedCost() method
     * - the Minister class has a getImprovementDiscountRate() method, to obtain the
     * discount rate of building a road
     *
     * @param player
     * @param city
     * @throws TooPoorException
     */
    public void buildRoad(Player player, City city) throws TooPoorException {
        // TODO
        Cost buildingRoadCost = city.getRoadCost();
        Cost discountedBuildingRoadCost = buildingRoadCost.getDiscountedCost(this.getImprovementDiscountRate());
        if(player.getGold() >= discountedBuildingRoadCost.getGold() && player.getProductionPoint() >= discountedBuildingRoadCost.getProduction()){
            player.decreaseGold(discountedBuildingRoadCost.getGold());
            player.decreaseProductionPoint(discountedBuildingRoadCost.getProduction());
            city.addRoad();
        }
        else{
            throw new TooPoorException(player, discountedBuildingRoadCost);
        }
    }

    /**
     * Build a university in the city
     * 1. Get the cost of building a university, with applied minister discount
     * 2. Check whether player has enough gold and production points
     * 3. If not, throw an exception
     * 4. Subtract the cost from the player's gold and production point
     * 5. Add number of university in the city by one
     * <p>
     * HINT:
     * - the Cost class has a getDiscountedCost() method
     * - the Minister class has a getImprovementDiscountRate() method, to obtain the
     * discount rate of building a university
     *
     * @param player
     * @param city
     * @throws TooPoorException
     */
    public void buildUniversity(Player player, City city) throws TooPoorException {
        // TODO
        Cost buildingUniversityCost = city.getUniversityCost();
        Cost discountedBuildingUniversityCost = buildingUniversityCost.getDiscountedCost(this.getImprovementDiscountRate());
        if(player.getGold() >= discountedBuildingUniversityCost.getGold() && player.getProductionPoint() >= discountedBuildingUniversityCost.getProduction()){
            player.decreaseGold(discountedBuildingUniversityCost.getGold());
            player.decreaseProductionPoint(discountedBuildingUniversityCost.getProduction());
            city.addUniversity();
        }
        else{
            throw new TooPoorException(player, discountedBuildingUniversityCost);
        }
    }

    /**
     * Attack a target city
     * Attacking city loses troops equal to min(# of troops attacking, # of troops in the defending city)
     * Defending city loses round(0.8 * # of attacking troops * product of tech attack bonuses)
     * <p>
     * This method is overridden in the WarGeneral class
     *
     * Print the following messages:
     * "[attacker city name] loses [number of troops lost by attacker] troops while attacking"
     * "[defender city name] loses [number of troops lost by defender] troops while defending"
     *
     * @param attacker        attacking city
     * @param defender        defending city
     * @param attackingTroops number of troops deployed for the attack
     * @param technologyList  technologies owned by the attacking player
     */
    public void attackCity(City attacker, City defender, int attackingTroops, List<Technology> technologyList) {
        // TODO

        attacker.decreaseTroops(Math.min(attackingTroops, defender.getTroops()));
        System.out.println(attacker.getName() + " loses " + Math.min(attackingTroops, defender.getTroops()) + " troops while attacking");

        int techProduct = 1;
        for(int i=0; i<technologyList.size(); i++) {
            techProduct *= technologyList.get(i).getAttackBonus();
        }

        int lostTroopsByDefender = (int)(Math.round((0.8 * attackingTroops * techProduct)));

        if(defender.getTroops() >= lostTroopsByDefender){   //if defending city has more number of troops than the troops lost by attacking city
            defender.decreaseTroops((int)(Math.round((0.8 * attackingTroops * techProduct))));
        }
        else{   //if defending city has less number of troops than the troops lost by attacking city
            defender.decreaseTroops(defender.getTroops());
        }
        System.out.println(defender.getName() + " loses " + (int)(Math.round((0.8 * attackingTroops * techProduct))) + " troops while defending");
    }

    /**
     * Improve the crop yields in a city
     * Improve Crop yields by 50 for 500 golds
     * If the player does not have enough gold, throw an exception
     * Else decrease 500 golds from the player and improves crops by 50
     * <p>
     * This method is overridden in the Economist class
     *
     * @param player
     * @param city
     * @throws TooPoorException
     */
    public void improveCropYield(Player player, City city) throws TooPoorException {
        // TODO
        if(player.getGold() >= 500){
            player.decreaseGold(500);
            city.improveCrops(50);
        }
        else{
            throw new TooPoorException(player, new Cost(500, 0,0));
        }
    }


    /**
     * Recruit troops to be stationed in a city
     * Recruit 50 troops for 500 golds
     * If the player does not have enough gold, throw an exception
     * <p>
     * Overridden in WarGeneral class
     *
     * @param player
     * @param city
     * @throws TooPoorException
     */
    public void recruitTroops(Player player, City city) throws TooPoorException {
        // TODO
        if(player.getGold() >= 500){
            player.decreaseGold(500);
            city.addTroops(50);
        }
        else{
            throw new TooPoorException(player, new Cost(500, 0, 0));
        }
    }

    /**
     * Upgrades tech
     * 1. Get the cost of upgrading tech, with applied minister discount
     * 2. Check whether player has enough gold, production, and science points
     * 3. If not, throw an exception
     * 4. Subtract the costs from the player's balance
     * 5. Add level of technology by one
     * <p>
     * HINT:
     * - the Cost class has a getDiscounterCost() method
     * - the Minister class has a getTechDiscountRate() method
     *
     * @param player
     * @param technology
     * @throws TooPoorException
     */
    public void upgradeTech(Player player, Technology technology) throws TooPoorException {
        // TODO
        Cost upgradeTechCost = technology.getUpgradeCost();
        Cost discountedUpgradeTechCost = upgradeTechCost.getDiscountedCost(this.getTechDiscountRate());
        if((player.getGold() >= discountedUpgradeTechCost.getGold()) && (player.getProductionPoint() >= discountedUpgradeTechCost.getProduction()) && (player.getSciencePoint() >= discountedUpgradeTechCost.getScience())){
            player.decreaseGold(discountedUpgradeTechCost.getGold());
            player.decreaseProductionPoint(discountedUpgradeTechCost.getProduction());
            player.decreaseSciencePoint(discountedUpgradeTechCost.getScience());
            technology.addLevel();
        }
        else{
            throw new TooPoorException(player, new Cost(discountedUpgradeTechCost.getGold(), discountedUpgradeTechCost.getProduction(), discountedUpgradeTechCost.getScience()));
        }
    }

    /**
     * This method is called when you want to spy on your neighbors.
     * <p>
     * Print the information of a City's neighbors
     * <p>
     * HINT:
     * - GameMap class has a getNeighboringCities() method
     * - City class overrides the toString() method which returns the
     * String representation of a city
     *
     * @param city
     */
    public void spyOnNeighbors(City city, GameMap map) {
        // TODO
        List<City> neighboringCities = map.getNeighboringCities(city);
        for(int i=0; i<neighboringCities.size(); i++){
            City neighboringCity = neighboringCities.get(i);
            System.out.println(neighboringCity.toString());
        }
    }

    /**
     * Minister by default does not provide improvement discount rate
     * therefore this method always returns 1.
     * <p>
     * This behavior may be overridden in a sub-class
     *
     * @return 1
     */
    public double getImprovementDiscountRate() {
        return 1;
    }

    /**
     * Minister by default does not provide tech discount rate
     * therefore this method always returns 1.
     * <p>
     * This behavior may be overridden in a sub-class
     *
     * @return 1
     */
    public double getTechDiscountRate() {
        return 1;
    }
}

