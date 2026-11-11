package csd.uoc.gr.A23.battle;

import csd.uoc.gr.A23.person.Member;
import csd.uoc.gr.A23.utilities.Utilities;
import csd.uoc.gr.A23.person.Character;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private String groupName;
    private List<Member> members;

    Group(String name) {
        this.members = new ArrayList<>();
        this.groupName = name;

    }

    String getGroupName() {
        return this.groupName;
    }

    int getSize() {
        return members.size();
    }

    int getNumberOfAliveMembers() {
        int alive = 0;
        for(int i=0;i<members.size();i++) {
            if(members.get(i).getHealth()>0) {
                alive++;
            }
        }
        return alive;
    }

    boolean isDefeated() {
        int alive = 0;
        for(int i=0;i<members.size();i++) {
            if(members.get(i).getHealth()>0) {
                alive++;
            }
        }
        if(alive==0) {
            return true;
        } else {
            return false;
        }
    }

    Member pickRandomAliveMember() {
        boolean b = false;
        int a=-1;
        while(!b) {
            if(!members.isEmpty()) {
                a = Utilities.getRandomVal(0,members.size()-1);
            } else {
                a = 0;
            }
            if(members.get(a).getHealth()>0) {
                b = true;
            }
        }
        return members.get(a);
    }

    void addMember(Character member) {
        if(member == null) {
            throw new IllegalArgumentException("Member is null!");
        } else {
            members.add((Member) member);
        }
    }
}
