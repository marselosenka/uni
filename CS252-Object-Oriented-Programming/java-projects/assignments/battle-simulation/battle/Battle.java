package csd.uoc.gr.A23.battle;
import java.util.Arrays;
import java.util.List;
import csd.uoc.gr.A23.armor.*;
import csd.uoc.gr.A23.exception.DeadCharacterException;
import java.util.logging.Level;
import java.util.logging.Logger;
import csd.uoc.gr.A23.person.HeistMastermind;
import csd.uoc.gr.A23.person.HeistMember;
import csd.uoc.gr.A23.person.Insider;
import csd.uoc.gr.A23.person.Member;
import csd.uoc.gr.A23.person.PoliceInspector;
import csd.uoc.gr.A23.person.PoliceOfficer;
import static csd.uoc.gr.A23.utilities.Utilities.getRandomVal;
/**
 * @author Xiroudakis Georgios
 */
public class Battle {
    public static void main(String[] args) {
        // List of names for Heist members
        List<String> heistMembersNames = Arrays.asList("Tokyo", "Berlin", "Athens", "Abinsk", "Oslo", "Minsk", "Vác",
                "Lima", "Lisbon", "Dublin", "tashkent", "Kingston", "Fieri");
        String HeistMastermindName = "Madrid";
        String HeistInsideName = "HeistInsider";
        // List of names for Police officers
        List<String> policeNames =Arrays.asList("Inspector Anna", "Officer Hawk", "Sergeant Blaze", "Detective Raven",
                "Officer George", "Lieutenant Alex", "Captain Falcon", "Officer Georgia",
                "Detective Pantelis", "Sergeant Phoenix", "Lieutenant Tonis", "Officer Gkampi",
                "Detective amas");
        String PoliceInspectorName = "Inspector Aguilar";
        String PoliceInsideName = "PoliceInsider";
        // Create groups
        Group heistGroup = createGroup("Heist", heistMembersNames, HeistMastermindName, HeistInsideName);
        Group policeGroup = createGroup("Police", policeNames, PoliceInspectorName, PoliceInsideName);
        // Printing information about the 2 groups before the battle
        printGroupInfo(heistGroup, heistMembersNames, HeistMastermindName, HeistInsideName);
        printGroupInfo(policeGroup, policeNames, PoliceInspectorName, PoliceInsideName);
        // Conduct the battle
        String winner = conductBattle(heistGroup, policeGroup);
        System.out.println(winner + " won!\n");
        // Printing alive members after the war
        printAliveMembersInfo(heistGroup, policeGroup);
    }
    // Method to get a random weapon
    private static Gun getRandomWeapon() {
        Gun[] weapons = {new Rifle(), new Handgun(), new Shotgun(), new Submachine()};
        return weapons[getRandomVal(0, weapons.length - 1)];
    }
    private static void printGroupInfo(Group group, List<String> memberNames, String specialMember1, String
            specialMember2) {
        System.out.println(group.getGroupName() + " has " + group.getSize() + " members: ");
        for (String name : memberNames) {
            System.out.println("- " + name);
        }
        System.out.println("- " + specialMember1);
        System.out.println("- " + specialMember2);
        System.out.println();
    }
    private static Group createGroup(String groupName, List<String> memberNames, String specialMemberName, String
            insiderName) {
        Group group = new Group(groupName);
        // Add regular members
        for (String name : memberNames) {
            Member member = groupName.equals("Heist")
                    ? new HeistMember(name, getRandomWeapon(), getRandomVal(0, 1) == 1, getRandomVal(1, 3),
                    getRandomVal(1, 3))
                    : new PoliceOfficer(name, getRandomWeapon(), getRandomVal(0, 1) == 1, getRandomVal(1, 3),
                    getRandomVal(1, 3));
            group.addMember(member);
        }
        // Add the mastermind or inspector
        Member specialMember = groupName.equals("Heist")
                ? new HeistMastermind(specialMemberName, getRandomWeapon(), getRandomVal(3,5), getRandomVal(3, 5))
                : new PoliceInspector(specialMemberName, getRandomWeapon(), getRandomVal(3, 5), getRandomVal(3, 5));
        group.addMember(specialMember);
        // Add the insider
        Insider insider = new Insider(insiderName, getRandomWeapon(), getRandomVal(0, 1) == 1,
                getRandomVal(3, 4), getRandomVal(3, 4));
        group.addMember(insider);
        return group;
    }
    private static String conductBattle(Group heistGroup, Group policeGroup) {
        boolean turn = true;
        System.out.println("The battle begun");
        // Repeat until one Group loses
        int round = 0;
        while (!(heistGroup.isDefeated() || policeGroup.isDefeated())) {
            Member attacker = turn ? policeGroup.pickRandomAliveMember() : heistGroup.pickRandomAliveMember();
            Member defender = turn ? heistGroup.pickRandomAliveMember() : policeGroup.pickRandomAliveMember();
            if (attacker == null || defender == null) {
                break;
            }
            if(turn) System.out.println("\nRound " + round++);
            System.out.println((turn ? "--------------------------------\n" : "") + attacker.getName() + " attacks " + defender.getName());
            try {
                attacker.fight(defender);
            } catch (DeadCharacterException ex) {
                Logger.getLogger(Battle.class.getName()).log(Level.SEVERE, null, ex);
            }
            turn = !turn;
        }
        System.out.println("\nThe battle ended in " + round + " rounds\n\n");
        // Determine the winner
        if (heistGroup.getNumberOfAliveMembers() > policeGroup.getNumberOfAliveMembers()) {
            return heistGroup.getGroupName();
        } else if (policeGroup.getNumberOfAliveMembers() > heistGroup.getNumberOfAliveMembers()) {
            return policeGroup.getGroupName();
        } else {
            return "Nobody"; // In case of a tie
        }
    }
    // Method to print alive members information
    private static void printAliveMembersInfo(Group heistGroup, Group policeGroup) {
        System.out.println(heistGroup.getGroupName() + " alive members: " + heistGroup.getNumberOfAliveMembers());
        System.out.println(policeGroup.getGroupName() + " alive members: " + policeGroup.getNumberOfAliveMembers());
    }
}