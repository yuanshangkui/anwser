package server;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class UserBank {
    private Set<Account> users = new HashSet<>();

    private Set<Group> groups = new HashSet<>();

    public int canLogin(String userId ,String password) {
        int state = 0;//不存在该账号
        for (Account user : users) {
            if (user.getUserId().equals(userId)) {
                if (user.getPassword().equals(password) && !user.isOnline()) {
                    user.setOnline(true);
                    state = 1; //存在且可登录
                } else if (!user.getPassword().equals(password)) {
                    state = -1;//密码错误
                } else {
                    state = -2;//用户已经在线
                }
                break;
            }
        }
        return state;
    }

    public Account getAccount(String id) {
        for (Account user : users) {
            if (user.getUserId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    public Group getGroup(String groupId) {
        for (Group group : groups) {
            if (group.getId().equals(groupId)) {
                return group;
            }
        }
        return null;
    }

    public void removeGroup(Group group) {
        this.groups.remove(group);
    }

    public boolean containAccount(String userId) {
        return getAccount(userId) != null;
    }

    public boolean containGroup(String groupId) {
        return getGroup(groupId) != null;
    }

    public void addAccount(String name, String userId, String password) {
        this.users.add(new Account(name,userId,password));
    }

    public void addGroup(String name, String groupId, Account leader) {
        this.groups.add(new Group(leader,name,groupId));
    }

    public String createAccountId() {
        return createId(10);
    }

    public String createGroupId() {
        return createId(8);
    }

    public String createId(int type) {
        Random creator = new Random();
        StringBuffer id;
        do {
            id = new StringBuffer();
            id.append((1 + creator.nextInt(9)));
            for (int i = 0; i < type - 1; i++) {
                id.append(creator.nextInt(10));
            }
        } while (containAccount(id.toString()));
        return id.toString();
    }
}
