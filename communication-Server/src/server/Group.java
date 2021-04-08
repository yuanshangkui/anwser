package server;

import java.io.Serializable;
import java.util.ArrayList;

public class Group implements Serializable {
    private static final long serialVersionUID = 1234556678988L;
    private Account leader;
    private String name;
    private String id;
    private ArrayList<Account> members = new ArrayList<>();

    public synchronized void sendGroupInformation(String from ,Information information) {
        for (Account a : this.members) {
            if (!a.getUserId().equals(from)) {
                a.getGroupInformation(this.id,information);
            }
        }
    }

    public Group(Account leader, String name, String id) {
        this.name = name;
        this.id = id;
        this.leader = leader;
        this.interGroup(leader);
        leader.addGroup(this);
    }

    public synchronized void interGroup(Account account) {
        this.members.add(account);
    }

    public synchronized void leaveGroup(Account account) {
        this.members.remove(account);
        if (account.equals(leader) && members.size() != 0) {
            for (Account a : members) {
                this.leader = a;
                break;
            }
        }
        if (this.members.size() == 0) {
            Server.userBank.removeGroup(this);
        }
    }

    public Account getMember(String userId) {
        for (Account member : members) {
            if (member.getUserId().equals(userId)) {
                return member;
            }
        }
        return null;
    }

    public Account getLeader() {
        return leader;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return this.name + " : " + this.id;
    }
}
