package listener;

import pojo.NormalUser;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Condition {
    //regex全部为中后缀
    private String nameRegex = "[\\u4e00-\\u9fa5]*";
    private String usernameRegex = "[0-9]*";
    private String emailRegex = "";

    private Map<String, String> conditions = new HashMap<>();

    public void add(String key, String value){
        conditions.put(key, value);
    }

    public boolean check(NormalUser normalUser){
        for (Map.Entry<String, String> entry:conditions.entrySet()){
            if ("name".equals(entry.getKey())){
                if (normalUser.getName()==null){
                    return false;
                }
                String regex = nameRegex+entry.getValue()+nameRegex;
                Pattern p = Pattern.compile(regex);
                Matcher m = p.matcher(normalUser.getName());
                return m.matches();
            }
            else if ("qq".equals(entry.getKey())){
                if (normalUser.getQq()==null){
                    return false;
                }
                String regex = usernameRegex+entry.getValue()+usernameRegex;
                Pattern p = Pattern.compile(regex);
                Matcher m = p.matcher(normalUser.getQq());
                return m.matches();
            }
            else if ("username".equals(entry.getKey())){
                if (normalUser.getUsername()==null){
                    return false;
                }
                String regex = usernameRegex+entry.getValue()+usernameRegex;
                Pattern p = Pattern.compile(regex);
                Matcher m = p.matcher(normalUser.getUsername());
                return m.matches();
            }else if ("phone".equals(entry.getKey())){
                if (normalUser.getPhoneNum()==null){
                    return false;
                }
                String regex = usernameRegex+entry.getValue()+usernameRegex;
                Pattern p = Pattern.compile(regex);
                Matcher m = p.matcher(normalUser.getPhoneNum());
                return m.matches();
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        return conditions.equals(obj);
    }

    @Override
    public int hashCode() {
        return conditions.hashCode();
    }
}
