package server;

import java.io.Serializable;
import java.util.*;

public class Account implements Serializable {
    private static final long serialVersionUID = 1234556678999L;
    private String userName;
    private String userId;
    private String password;
    private boolean online = false;
    private ArrayList<Account> friends = new ArrayList<>();

    private Map<String,Queue<Information>> friendsInformation = new HashMap<>();

    private ArrayList<Group> groups = new ArrayList<>();
    private Map<String,Queue<Information>> groupsInformation = new HashMap<>();
    private ArrayList<String> applicantsOfAddGroup = new ArrayList<>();
    private ArrayList<Account> applicantsOfFriend = new ArrayList<>();
    private ArrayList<String> groupInvitation = new ArrayList<>();

    public Account(String userName, String userId, String password) {
        this.userName = userName;
        this.userId = userId;
        this.password = password;
    }

    public void getGroupInformation(String groupId ,Information information) {
        for (Map.Entry<String,Queue<Information>> groupInformation : this.groupsInformation.entrySet()) {
            if (groupInformation.getKey().equals(groupId)) {
                groupInformation.getValue().offer(information);
            }
        }
    }

    public Queue<Information> getFriendSendQueue(String friendId) {
        for (Account account : friends) {
            //找出对方账户
            if (account.getUserId().equals(friendId)) {
                //找到对方账户中存储该用户消息的队列
                for (Map.Entry<String,Queue<Information>> entry:account.friendsInformation.entrySet()) {
                    if (entry.getKey().equals(this.userId)) {
                        return entry.getValue();
                    }
                }
            }
        }
        return null;
    }

    public Queue<Information> getGroupReceiveQueue(String groupId) {
        for (Map.Entry<String,Queue<Information>> entry :this.groupsInformation.entrySet()) {
            if (entry.getKey().equals(groupId)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public Queue<Information> getFriendReceiveQueue(String friendId) {
        for (Map.Entry<String,Queue<Information>> entry :this.friendsInformation.entrySet()) {
            if (entry.getKey().equals(friendId)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public void outGroup(Group group) {
        this.groups.remove(group);
        for (Map.Entry<String,Queue<Information>> m : this.groupsInformation.entrySet()) {
            if (m.getKey().equals(group.getId())) {
                this.groupsInformation.remove(m.getKey(),m.getValue());
                break;
            }
        }
    }

    public synchronized void addGroupInvitation(String groupId) {
        this.groupInvitation.add(groupId);
    }

    public void removeGroupInvitation(String groupId) {
        this.groupInvitation.remove(groupId);
    }

    public void addFriend(Account account) {
        this.friends.add(account);
        this.friendsInformation.put(account.userId,new LinkedList<>());
    }

    public void addGroup(Group group) {
        this.groups.add(group);
        this.groupsInformation.put(group.getId(),new LinkedList<>());
    }

    public synchronized void addFriendApplicant(Account account) {
        this.applicantsOfFriend.add(account);
    }

    public synchronized void addGroupApplicant(String applicantIdAndGroupId) {
        this.applicantsOfAddGroup.add(applicantIdAndGroupId);
    }

    public void removeGroupApplicant(String applicantIdAndGroupId) {
        this.applicantsOfAddGroup.remove(applicantIdAndGroupId);
    }

    public void removeFriendApplicant(Account account) {
        this.applicantsOfFriend.remove(account);
    }

    public boolean containFriend(String friendId) {
        for (Account friend : friends) {
            if (friend.getUserId().equals(friendId)) {
                return true;
            }
        }
        return false;
    }

    public boolean containGroup(String groupId) {
        for (Group group : groups) {
            if (group.getId().equals(groupId)) {
                return true;
            }
        }
        return false;
    }

    public boolean containFriendApplicant(String applicantId) {
        for (Account applicant : applicantsOfFriend) {
            if (applicant.getUserId().equals(applicantId)) {
                return true;
            }
        }
        return false;
    }

    public boolean containGroupApplicant(String applicantIdAndGroupId) {
        for (String applicantIdGroupId : applicantsOfAddGroup) {
            if (applicantIdGroupId.equals(applicantIdAndGroupId)) {
                return true;
            }
        }
        return false;
    }

    public Account getFriend(String friendId) {
        for (Account friend : friends) {
            if (friend.getUserId().equals(friendId)) {
                return friend;
            }
        }
        return null;
    }

    public Group getGroup(String groupId) {
        for (Group group : this.groups) {
            if (group.getId().equals(groupId)) {
                return group;
            }
        }
        return null;
    }

    public String[] getGroupsToString() {
        String[] groupsToString = new String[this.groups.size()];
        for (int i = 0; i < this.groups.size(); i++) {
            groupsToString[i] = groups.get(i).toString();
        }
        return groupsToString;
    }

    public String[] getFriendsToString() {
        return getToString(friends);
    }

    public String[] getFriendApplicantsToString() {
        return getToString(this.applicantsOfFriend);
    }

    public String[] getAddGroupApplicationToString() {
        String[] s = new String[this.applicantsOfAddGroup.size()];
        for (int i = 0; i < this.applicantsOfAddGroup.size(); i++) {
            s[i] = this.applicantsOfAddGroup.get(i);
        }
        return s;
    }

    public String[] getGroupInvitationToString() {
        String[] s = new String[this.groupInvitation.size()];
        for (int i = 0; i < this.groupInvitation.size(); i++) {
            s[i] = this.groupInvitation.get(i);
        }
        return s;
    }

    private String[] getToString(ArrayList<Account> applicants) {
        String[] applicantsToString = new String[applicants.size()];
        for (int i = 0; i < applicants.size(); i++) {
            applicantsToString[i] = applicants.get(i).toString();
        }
        return applicantsToString;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    @Override
    public String toString() {
        String s = userName + " : " + userId + " : ";
        if (this.online)
            s += "在线";
        else
            s += "离线";
        return s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(userId, account.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, userId, password, online, friends, groups);
    }

}