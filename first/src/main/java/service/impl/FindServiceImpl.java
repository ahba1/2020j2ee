package service.impl;

import com.alibaba.excel.EasyExcel;
import javafx.util.Pair;
import listener.Condition;
import listener.NormalUserListener;
import pojo.NormalUser;
import service.FindService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindServiceImpl implements FindService {

    private NormalUserListener listener = new NormalUserListener();
    private Map<Pair<String, String>, List<NormalUser>> cache = new HashMap<>();

    @Override
    public List<NormalUser> find(String path, String key, String value) {
        final Condition condition = new Condition();
        condition.add(key, value);
        final Pair<String, String> pair = new Pair<>(key, value);
        if (cache.containsKey(pair)) {
        }
        else{
            listener.setCondition(condition);
            listener.setCallBack(new NormalUserListener.CallBack() {
                @Override
                public void onFinished(List<NormalUser> users) {
                    cache.put(pair, users);
                }
            });
            EasyExcel.read(path, NormalUser.class, listener).sheet().doRead();
        }
        return cache.get(pair);
    }
}
