package listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.SneakyThrows;
import pojo.NormalUser;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class NormalUserListener extends AnalysisEventListener<NormalUser> {

    private List<NormalUser> data = new LinkedList<>();
    private int batchSize = 10;
    private Condition condition;
    private CallBack callBack;

    public interface CallBack{
        void onFinished(List<NormalUser> users) throws IOException;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public CallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void invoke(NormalUser normalUser, AnalysisContext context) {
        Logger.getGlobal().info(normalUser.getName()+normalUser.getUsername());
        if (condition.check(normalUser)){
            data.add(normalUser);
        }
        if (data.size()>=batchSize){
            outPutData();
            data.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        outPutData();
        data.clear();
    }

    @SneakyThrows
    private void outPutData() {
        if (callBack!=null){
            callBack.onFinished(data);
        }
    }

    public int getBatchSize(){
        return this.batchSize;
    }

    public void setBatchSize(int batchSize){
        this.batchSize = batchSize;
    }
}
